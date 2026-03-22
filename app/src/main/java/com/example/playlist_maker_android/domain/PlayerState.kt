package com.example.playlist_maker_android.domain

sealed class QueueSource {
    data object Search : QueueSource()
    data object Favourites : QueueSource()
    data class Playlist(val playlistId: Long) : QueueSource()
    data object Single : QueueSource()
}

data class PlayerState(
    val currentTrack: Track? = null,
    val isPlaying: Boolean = false,
    val currentPositionMs: Int = 0,
    val durationMs: Int = 0,
    val queue: List<Track> = emptyList(),
    val currentIndex: Int = -1,
    val queueSource: QueueSource = QueueSource.Single,
) {
    val hasNext: Boolean get() = currentIndex < queue.lastIndex
    val hasPrevious: Boolean get() = currentIndex > 0
}
