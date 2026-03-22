package com.example.playlist_maker_android.domain

import kotlinx.coroutines.flow.StateFlow

interface PlayerRepository {
    val playerState: StateFlow<PlayerState>
    fun play(track: Track)
    fun playQueue(queue: List<Track>, startIndex: Int, source: QueueSource)
    fun updateQueue(queue: List<Track>)
    fun addToQueue(track: Track)
    fun next()
    fun previous()
    fun pause()
    fun resume()
    fun stop()
    fun seekTo(positionMs: Int)
}
