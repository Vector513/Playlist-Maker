package com.example.playlist_maker_android.domain

import com.example.playlist_maker_android.data.network.Track

interface TracksRepository {
    suspend fun searchTracks(expression: String): List<Track>
}