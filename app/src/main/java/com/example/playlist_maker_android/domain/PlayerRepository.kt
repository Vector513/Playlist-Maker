package com.example.playlist_maker_android.domain

import kotlinx.coroutines.flow.StateFlow

interface PlayerRepository {
    val playerState: StateFlow<PlayerState>
    fun play(track: Track)
    fun pause()
    fun resume()
    fun stop()
    fun seekTo(positionMs: Int)
}
