package com.example.playlist_maker_android.domain

import kotlinx.coroutines.flow.Flow

interface TracksRepository {
    suspend fun searchTracks(expression: String): List<Track>
    fun getTrackByNameAndArtist(track: Track): Flow<Track?>
    suspend fun getTrackById(id: Long): Flow<Track?>
    fun getFavoriteTracks(): Flow<List<Track>>
    suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean)
}