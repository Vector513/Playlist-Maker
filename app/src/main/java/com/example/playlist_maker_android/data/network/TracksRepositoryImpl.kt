package com.example.playlist_maker_android.data.network

import com.example.playlist_maker_android.data.DatabaseMock
import com.example.playlist_maker_android.data.dto.TracksSearchRequest
import com.example.playlist_maker_android.data.dto.TracksSearchResponse
import com.example.playlist_maker_android.domain.TracksRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.CoroutineScope as KtCoroutineScope

class TracksRepositoryImpl(
    private val scope: CoroutineScope = KtCoroutineScope(Dispatchers.IO),
    private val database: DatabaseMock = DatabaseMock(scope = scope)
) : TracksRepository {
    

    override suspend fun getAllTracks(): List<Track> {
        return database.searchTracks("")
    }

    override suspend fun searchTracks(expression: String): List<Track> {
        return database.searchTracks(expression)
    }

    override fun getTrackByNameAndArtist(track: Track): Flow<Track?> {
        return database.getTrackByNameAndArtist(track)
    }

    override suspend fun insertTrackToPlaylist(track: Track, playlistId: Long) {
        database.insertTrack(track.copy(playlistId = playlistId))
    }

    override suspend fun deleteTrackFromPlaylist(track: Track) {
        database.insertTrack(track.copy(playlistId = 0))
    }

    override suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean) {
        database.insertTrack(track.copy(favorite = isFavorite))
    }

    override suspend fun deleteTracksByPlaylistId(playlistId: Long) {
        database.deleteTracksByPlaylistId(playlistId)
    }

    override fun getFavoriteTracks(): Flow<List<Track>> {
        return database.getFavoriteTracks()
    }
}