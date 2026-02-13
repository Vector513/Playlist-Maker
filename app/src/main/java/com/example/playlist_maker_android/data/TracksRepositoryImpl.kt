package com.example.playlist_maker_android.data

import android.util.Log
import androidx.room.Transaction
import com.example.playlist_maker_android.data.database.AppDatabase
import com.example.playlist_maker_android.data.database.entity.toTrack
import com.example.playlist_maker_android.data.dto.TracksSearchRequest
import com.example.playlist_maker_android.data.dto.TracksSearchResponse
import com.example.playlist_maker_android.data.dto.toTrack
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
                            dto.toTrack()
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
                            response.results[0].toTrack()
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

//    override suspend fun insertTrackToPlaylist(track: Track, playlistId: Long) {
//        dao
//    }

    @Transaction
    override suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean) {
        val trackEntity = track.toEntity(favorite = isFavorite)

        val exists = dao.exists(trackEntity.id)

        if (exists) {
            dao.updateTrack(trackEntity)
            Log.i("db", "favorite updated to $isFavorite for trackId=${track.id}")
        } else {
            dao.insertTrack(trackEntity)
            Log.i("db", "favorite inserted to $isFavorite for trackId=${track.id}")
        }

        if (!isFavorite) {
            val playlistsCount = dao.getPlaylistsCountForTrack(track.id)
            Log.i("db", "  $playlistsCount")
            if (playlistsCount == 0) {
                dao.deleteTrack(track.toEntity(favorite = false))
                Log.i("db", "trackId=${track.id} deleted as non-favorite and not in playlists")
            }
        }
    }
}