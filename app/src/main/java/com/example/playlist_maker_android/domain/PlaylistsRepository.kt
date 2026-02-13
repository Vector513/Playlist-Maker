package com.example.playlist_maker_android.domain

import kotlinx.coroutines.flow.Flow

interface PlaylistsRepository {
    fun getPlaylist(playlistId: Long): Flow<Playlist?>

    fun getAllPlaylists(): Flow<List<Playlist>>

    suspend fun addNewPlaylist(playlist: Playlist)

    suspend fun addTrackToPlaylist(track: Track, playlistId: Long)

    suspend fun deletePlaylistById(id: Long)

    suspend fun removeTrackFromPlaylist(trackId: Long, playlistId: Long)

    suspend fun removeAllTracksFromPlaylist(playlistId: Long)
}