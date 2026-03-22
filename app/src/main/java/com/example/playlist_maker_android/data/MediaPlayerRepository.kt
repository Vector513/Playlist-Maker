package com.example.playlist_maker_android.data

import android.media.MediaPlayer
import com.example.playlist_maker_android.data.cache.PreviewCacheManager
import com.example.playlist_maker_android.domain.PlayerRepository
import com.example.playlist_maker_android.domain.PlayerState
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
    private val cacheManager: PreviewCacheManager
) : PlayerRepository {

    private var mediaPlayer: MediaPlayer? = null
    private val _playerState = MutableStateFlow(PlayerState())
    override val playerState: StateFlow<PlayerState> = _playerState.asStateFlow()

    private var progressJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun play(track: Track) {
        val url = track.previewUrl ?: return
        stop()
        val cachedFile = cacheManager.getCachedFile(track.id)
        val source = cachedFile?.absolutePath ?: url
        mediaPlayer = MediaPlayer().apply {
            setDataSource(source)
            setOnPreparedListener { mp ->
                _playerState.value = PlayerState(
                    currentTrack = track,
                    isPlaying = true,
                    currentPositionMs = 0,
                    durationMs = mp.duration
                )
                mp.start()
                startProgressUpdates()
            }
            setOnCompletionListener {
                progressJob?.cancel()
                _playerState.update { it.copy(isPlaying = false, currentPositionMs = it.durationMs) }
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
        progressJob?.cancel()
        mediaPlayer?.release()
        mediaPlayer = null
        _playerState.value = PlayerState()
    }

    override fun seekTo(positionMs: Int) {
        mediaPlayer?.seekTo(positionMs)
        _playerState.update { it.copy(currentPositionMs = positionMs) }
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

    companion object {
        private const val PROGRESS_UPDATE_INTERVAL_MS = 300L
    }
}
