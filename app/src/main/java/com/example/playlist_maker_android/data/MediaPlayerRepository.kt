package com.example.playlist_maker_android.data

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import com.example.playlist_maker_android.data.cache.PreviewCacheManager
import com.example.playlist_maker_android.service.PlaybackService
import com.example.playlist_maker_android.domain.PlayerRepository
import com.example.playlist_maker_android.domain.PlayerState
import com.example.playlist_maker_android.domain.QueueSource
import com.example.playlist_maker_android.domain.Track
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MediaPlayerRepository(
    private val context: Context,
    private val cacheManager: PreviewCacheManager
) : PlayerRepository {

    private var mediaPlayer: MediaPlayer? = null
    private val _playerState = MutableStateFlow(PlayerState())
    override val playerState: StateFlow<PlayerState> = _playerState.asStateFlow()

    private var progressJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun play(track: Track) {
        playQueue(listOf(track), 0, QueueSource.Single)
    }

    override fun playQueue(queue: List<Track>, startIndex: Int, source: QueueSource) {
        val tracksWithPreview = queue.filter { it.previewUrl != null }
        if (tracksWithPreview.isEmpty()) return

        val adjustedIndex = if (startIndex < tracksWithPreview.size) startIndex
            else tracksWithPreview.indexOfFirst { it.id == queue.getOrNull(startIndex)?.id }
                .coerceAtLeast(0)

        _playerState.update {
            it.copy(queue = tracksWithPreview, currentIndex = adjustedIndex, queueSource = source)
        }
        startTrack(tracksWithPreview[adjustedIndex])
    }

    override fun updateQueue(queue: List<Track>) {
        val tracksWithPreview = queue.filter { it.previewUrl != null }
        val state = _playerState.value
        val currentTrack = state.currentTrack ?: return

        val newIndex = tracksWithPreview.indexOfFirst { it.id == currentTrack.id }
        if (newIndex == -1) {
            // Current track was removed from the source list, keep playing but update queue
            val updatedQueue = listOf(currentTrack) + tracksWithPreview
            _playerState.update { it.copy(queue = updatedQueue, currentIndex = 0) }
        } else {
            _playerState.update { it.copy(queue = tracksWithPreview, currentIndex = newIndex) }
        }
    }

    override fun addToQueue(track: Track) {
        if (track.previewUrl == null) return
        val state = _playerState.value
        if (state.queue.any { it.id == track.id }) return
        _playerState.update { it.copy(queue = it.queue + track) }
    }

    override fun next() {
        val state = _playerState.value
        if (!state.hasNext) return
        val nextIndex = state.currentIndex + 1
        _playerState.update { it.copy(currentIndex = nextIndex) }
        startTrack(state.queue[nextIndex])
    }

    override fun previous() {
        val state = _playerState.value
        if (!state.hasPrevious) return
        val prevIndex = state.currentIndex - 1
        _playerState.update { it.copy(currentIndex = prevIndex) }
        startTrack(state.queue[prevIndex])
    }

    private fun startTrack(track: Track) {
        val url = track.previewUrl ?: return
        releasePlayer()
        val cachedFile = cacheManager.getCachedFile(track.id)
        val source = cachedFile?.absolutePath ?: url
        mediaPlayer = MediaPlayer().apply {
            setDataSource(source)
            setOnPreparedListener { mp ->
                _playerState.update {
                    it.copy(
                        currentTrack = track,
                        isPlaying = true,
                        currentPositionMs = 0,
                        durationMs = mp.duration
                    )
                }
                mp.start()
                startProgressUpdates()
                startService()
            }
            setOnCompletionListener {
                progressJob?.cancel()
                val state = _playerState.value
                if (state.hasNext) {
                    next()
                } else {
                    _playerState.update {
                        it.copy(isPlaying = false, currentPositionMs = it.durationMs)
                    }
                }
            }
            setOnErrorListener { _, _, _ ->
                stop()
                true
            }
            prepareAsync()
        }
    }

    override fun pause() {
        mediaPlayer?.pause()
        progressJob?.cancel()
        _playerState.update { it.copy(isPlaying = false) }
    }

    override fun resume() {
        mediaPlayer?.start()
        _playerState.update { it.copy(isPlaying = true) }
        startProgressUpdates()
    }

    override fun stop() {
        releasePlayer()
        _playerState.value = PlayerState()
    }

    override fun seekTo(positionMs: Int) {
        mediaPlayer?.seekTo(positionMs)
        _playerState.update { it.copy(currentPositionMs = positionMs) }
    }

    private fun releasePlayer() {
        progressJob?.cancel()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun startProgressUpdates() {
        progressJob?.cancel()
        progressJob = scope.launch {
            while (isActive) {
                mediaPlayer?.let { mp ->
                    _playerState.update { it.copy(currentPositionMs = mp.currentPosition) }
                }
                delay(PROGRESS_UPDATE_INTERVAL_MS)
            }
        }
    }

    private fun startService() {
        context.startForegroundService(Intent(context, PlaybackService::class.java))
    }

    companion object {
        private const val PROGRESS_UPDATE_INTERVAL_MS = 300L
    }
}
