package com.example.playlist_maker_android.domain

import com.example.playlist_maker_android.data.network.Track
import kotlinx.coroutines.flow.Flow

interface TracksRepository {
    suspend fun getAllTracks(): List<Track>
    suspend fun searchTracks(expression: String): List<Track>

    fun getTrackByNameAndArtist(track: Track): Flow<Track?>

    fun getFavoriteTracks(): Flow<List<Track>>

    suspend fun insertTrackToPlaylist(track: Track, playlistId: Long)

    suspend fun deleteTrackFromPlaylist(track: Track)
    suspend fun deleteTracksByPlaylistId(playlistId: Long)

    suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean)
}