package com.example.playlist_maker_android.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.playlist_maker_android.domain.PlayerRepository
import com.example.playlist_maker_android.domain.PlayerState
import com.example.playlist_maker_android.domain.QueueSource
import com.example.playlist_maker_android.domain.Track
import kotlinx.coroutines.flow.StateFlow

class PlayerViewModel(
    private val playerRepository: PlayerRepository
) : ViewModel() {

    val playerState: StateFlow<PlayerState> = playerRepository.playerState

    fun playTrack(track: Track) {
        playerRepository.play(track)
    }

    fun playFromQueue(queue: List<Track>, track: Track, source: QueueSource) {
        val index = queue.indexOfFirst { it.id == track.id }.coerceAtLeast(0)
        playerRepository.playQueue(queue, index, source)
    }

    fun updateQueue(queue: List<Track>) {
        playerRepository.updateQueue(queue)
    }

    fun addToQueue(track: Track) {
        playerRepository.addToQueue(track)
    }

    fun togglePlayPause() {
        val state = playerState.value
        if (state.currentTrack == null) return
        if (state.isPlaying) playerRepository.pause() else playerRepository.resume()
    }

    fun next() {
        playerRepository.next()
    }

    fun previous() {
        playerRepository.previous()
    }

    fun stop() {
        playerRepository.stop()
    }

    fun seekTo(positionMs: Int) {
        playerRepository.seekTo(positionMs)
    }
}
