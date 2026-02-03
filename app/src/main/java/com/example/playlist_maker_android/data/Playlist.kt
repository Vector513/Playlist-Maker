package com.example.playlist_maker_android.data

import com.example.playlist_maker_android.data.network.Track

data class Playlist(
    val id: Long = 0,
    val name: String,
    val description: String,
    var tracks: List<Track>
)