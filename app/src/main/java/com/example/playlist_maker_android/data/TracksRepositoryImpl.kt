package com.example.playlist_maker_android.data

import androidx.room.withTransaction
import com.example.playlist_maker_android.data.database.AppDatabase
import com.example.playlist_maker_android.data.database.entity.toTrack
import com.example.playlist_maker_android.data.dto.TracksSearchRequest
import com.example.playlist_maker_android.data.dto.TracksSearchResponse
import com.example.playlist_maker_android.data.dto.toTrack
import com.example.playlist_maker_android.data.dto.BaseResponse
import com.example.playlist_maker_android.data.network.NetworkClient
import com.example.playlist_maker_android.domain.ServerErrorException
import com.example.playlist_maker_android.domain.Track
import com.example.playlist_maker_android.domain.TracksRepository
import com.example.playlist_maker_android.data.cache.PreviewCacheManager
import com.example.playlist_maker_android.data.database.entity.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TracksRepositoryImpl(
    private val networkClient: NetworkClient,
    private val database: AppDatabase,
    private val cacheManager: PreviewCacheManager
) : TracksRepository {

    private val dao = database.TracksDao()

    override suspend fun searchTracks(expression: String, limit: Int): List<Track> {
        val response = networkClient.search(TracksSearchRequest(expression, limit = limit))
        return when (response) {
            is TracksSearchResponse -> {
                response.results.mapNotNull { dto ->
                    try {
                        dto.toTrack()
                    } catch (_: Exception) {
                        null
                    }
                }
            }
            is BaseResponse -> {
                if (response.resultCode in 400..<600) {
                    throw ServerErrorException(response.errorMessage ?: "Server error")
                } else {
                    emptyList()
                }
            }
        }
    }

    override fun getTrackByNameAndArtist(track: Track): Flow<Track?> {
        return dao.getTrackByNameAndArtist(track.trackName, track.artistName).map { it?.toTrack() }
    }

    override suspend fun getTrackById(id: Long): Track? {
        val cached = dao.getTrackByIdOnce(id)
        if (cached != null) return cached.toTrack()

        val response = networkClient.getTrackById(id)
        return when (response) {
            is TracksSearchResponse -> {
                response.results.firstOrNull()?.toTrack()
            }
            is BaseResponse -> null
        }
    }

    override fun getFavoriteTracks(): Flow<List<Track>> {
        return dao.getTracksForFavorites().map { list -> list.map { it.toTrack() } }
    }

    override suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean) {
        database.withTransaction {
            val trackEntity = track.toEntity(favorite = isFavorite)
            val exists = dao.exists(trackEntity.id)

            if (exists) {
                dao.updateTrack(trackEntity)
            } else {
                dao.insertTrack(trackEntity)
            }

            if (!isFavorite) {
                val playlistsCount = dao.getPlaylistsCountForTrack(track.id)
                if (playlistsCount == 0) {
                    dao.deleteTrack(track.toEntity(favorite = false))
                    cacheManager.deleteCache(track.id)
                }
            }
        }

        if (isFavorite) {
            track.previewUrl?.let { cacheManager.cachePreview(track.id, it) }
        }
    }
}
