package com.example.playlist_maker_android.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.playlist_maker_android.domain.PlayerRepository
import com.example.playlist_maker_android.domain.PlayerState
import com.example.playlist_maker_android.domain.Track
import kotlinx.coroutines.flow.StateFlow

class PlayerViewModel(
    private val playerRepository: PlayerRepository
) : ViewModel() {

    val playerState: StateFlow<PlayerState> = playerRepository.playerState

    fun playTrack(track: Track) {
        playerRepository.play(track)
    }

    fun togglePlayPause() {
        val state = playerState.value
        if (state.currentTrack == null) return
        if (state.isPlaying) playerRepository.pause() else playerRepository.resume()
    }

    fun stop() {
        playerRepository.stop()
    }

    fun seekTo(positionMs: Int) {
        playerRepository.seekTo(positionMs)
    }

}
