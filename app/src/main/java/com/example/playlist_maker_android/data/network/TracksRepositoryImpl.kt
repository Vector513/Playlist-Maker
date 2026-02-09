package com.example.playlist_maker_android.data.network

import android.util.Log
import com.example.playlist_maker_android.data.DatabaseMock
import com.example.playlist_maker_android.data.dto.TracksSearchRequest
import com.example.playlist_maker_android.data.dto.TracksSearchResponse
import com.example.playlist_maker_android.data.mapper.TrackMapper
import com.example.playlist_maker_android.domain.BaseResponse
import com.example.playlist_maker_android.domain.NetworkClient
import com.example.playlist_maker_android.domain.Track
import com.example.playlist_maker_android.domain.TracksRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class TracksRepositoryImpl(
    private val database: DatabaseMock,
    private val networkClient: NetworkClient
) : TracksRepository {

    override suspend fun searchTracks(expression: String): List<Track> {
        val response = networkClient.search(TracksSearchRequest(expression))
        Log.i("network", "searchTracks response code: ${response.resultCode}")
        return when (response) {
            is TracksSearchResponse -> {
                if (response.results.isNotEmpty()) {
                    response.results.mapNotNull { dto ->
                        try {
                            Log.i("networkTrackId", "$dto")
                            TrackMapper.fromDto(dto)
                        } catch (e: Exception) {
                            Log.e("network", "Error mapping track: ${e.message}", e)
                            null
                        }
                    }
                } else {
                    Log.i("network", "searchTracks: empty results for expression='$expression'")
                    emptyList()
                }
            }
            is BaseResponse -> {
                Log.e("network", "searchTracks error: ${response.errorMessage}")
                emptyList()
            }
        }
    }

    override suspend fun getTrackById(id: Long): Track? {
        val response = networkClient.getTrackById(id)
        Log.i("network", "getTrackById response code: ${response.resultCode}")
        return when (response) {
            is TracksSearchResponse -> {
                if (response.results.isNotEmpty()) {
                    TrackMapper.fromDto(response.results[0])
                } else {
                    Log.w("network", "getTrackById: empty results for id=$id")
                    null
                }
            }
            is BaseResponse -> {
                Log.e("network", "getTrackById error: ${response.errorMessage}")
                null
            }
        }
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
