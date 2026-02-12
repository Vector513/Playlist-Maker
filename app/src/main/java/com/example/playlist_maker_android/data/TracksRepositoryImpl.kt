package com.example.playlist_maker_android.data

import android.util.Log
import com.example.playlist_maker_android.data.database.AppDatabase
import com.example.playlist_maker_android.data.database.entity.toTrack
import com.example.playlist_maker_android.data.dto.TracksSearchRequest
import com.example.playlist_maker_android.data.dto.TracksSearchResponse
import com.example.playlist_maker_android.data.mapper.TrackMapper
import com.example.playlist_maker_android.domain.BaseResponse
import com.example.playlist_maker_android.domain.NetworkClient
import com.example.playlist_maker_android.domain.ServerErrorException
import com.example.playlist_maker_android.domain.Track
import com.example.playlist_maker_android.domain.TracksRepository
import com.example.playlist_maker_android.domain.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TracksRepositoryImpl(
    private val networkClient: NetworkClient,
    database: AppDatabase
) : TracksRepository {

    private val dao = database.TracksDao()

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
                // Если resultCode указывает на HTTP ошибку сервера (4xx, 5xx)
                if (response.resultCode >= 400 && response.resultCode < 600) {
                    Log.e("network", "searchTracks server error: ${response.errorMessage}")
                    throw ServerErrorException(response.errorMessage ?: "Ошибка сервера")
                } else {
                    // Сетевые ошибки или другие ошибки обрабатываются как обычные ошибки
                    Log.e("network", "searchTracks error: ${response.errorMessage}")
                    emptyList()
                }
            }
        }
    }

    override fun getTrackByNameAndArtist(track: Track): Flow<Track?> {
        return dao.getTrackByNameAndArtist(track.trackName, track.artistName).map { it?.toTrack() }
    }

    override suspend fun getTrackById(id: Long): Flow<Track?> {
        return dao.getTrackById(id).map {
            if (it != null) {
                it.toTrack()
            }
            else {
                val response = networkClient.getTrackById(id)
                Log.i("network", "getTrackById response code: ${response.resultCode}")
                when (response) {
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
        }
    }

    override fun getFavoriteTracks(): Flow<List<Track>> {
        return dao.getTracksForFavorites().map { list -> list.map { it.toTrack() } }
    }

    override fun getTracksForPlaylist(playlistId: Long): Flow<List<Track>> {
        return dao.getTracksForPlaylist(playlistId).map { list -> list.map { it.toTrack() } }
    }

    override suspend fun insertTrackToPlaylist(track: Track, playlistId: Long) {
        dao.insertOrUpdateTrack(track.toEntity(playlistId = playlistId))
    }

    override suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean) {
        dao.insertOrUpdateTrack(track.toEntity(favorite = isFavorite))
    }

    override suspend fun deleteTrackFromPlaylist(track: Track) {
        dao.updateTrack(track.toEntity(playlistId = 0))
    }

    override suspend fun deleteTracksByPlaylistId(playlistId: Long) {
        dao.deleteTracksByPlaylistId(playlistId)
    }
}