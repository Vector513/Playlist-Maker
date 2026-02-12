package com.example.playlist_maker_android.domain

import kotlinx.coroutines.flow.Flow

interface TracksRepository {
    suspend fun searchTracks(expression: String): List<Track>
    fun getTrackByNameAndArtist(track: Track): Flow<Track?>
    suspend fun getTrackById(id: Long): Flow<Track?>
    fun getFavoriteTracks(): Flow<List<Track>>
    fun getTracksForPlaylist(playlistId: Long): Flow<List<Track>>
    // TODO: add ability for Track to be in many Playlists
    suspend fun insertTrackToPlaylist(track: Track, playlistId: Long)
    suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean)
    suspend fun deleteTrackFromPlaylist(track: Track)
    suspend fun deleteTracksByPlaylistId(playlistId: Long)
}