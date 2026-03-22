package com.example.playlist_maker_android.data

import android.database.sqlite.SQLiteConstraintException
import androidx.room.withTransaction
import com.example.playlist_maker_android.data.database.AppDatabase
import com.example.playlist_maker_android.data.database.entity.PlaylistEntity
import com.example.playlist_maker_android.data.database.entity.PlaylistTrackCrossRefEntity
import com.example.playlist_maker_android.data.cache.PreviewCacheManager
import com.example.playlist_maker_android.data.database.entity.toEntity
import com.example.playlist_maker_android.data.database.toPlaylist
import com.example.playlist_maker_android.domain.Playlist
import com.example.playlist_maker_android.domain.PlaylistsRepository
import com.example.playlist_maker_android.domain.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlaylistsRepositoryImpl(
    private val database: AppDatabase,
    private val cacheManager: PreviewCacheManager
) : PlaylistsRepository {
    private val playlistsDao = database.PlaylistsDao()
    private val tracksDao = database.TracksDao()

    override fun getPlaylist(playlistId: Long): Flow<Playlist?> {
        return playlistsDao.getPlaylistWithTracks(playlistId).map { it?.toPlaylist() }
    }

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return playlistsDao.getPlaylistsWithTracks().map { list -> list.map { it.toPlaylist() } }
    }

    override suspend fun addNewPlaylist(playlist: Playlist): Boolean {
        return try {
            playlistsDao.insertPlaylist(playlist.toEntity())
            true
        } catch (_: SQLiteConstraintException) {
            false
        }
    }

    override suspend fun addTrackToPlaylist(track: Track, playlistId: Long) {
        database.withTransaction {
            val trackEntity = track.toEntity()

            val exists = tracksDao.exists(trackEntity.id)

            if (exists) {
                tracksDao.updateTrack(trackEntity)
            } else {
                tracksDao.insertTrack(trackEntity)
            }

            playlistsDao.addTrackToPlaylist(
                PlaylistTrackCrossRefEntity(playlistId = playlistId, trackId = trackEntity.id)
            )
        }

        track.previewUrl?.let { cacheManager.cachePreview(track.id, it) }
    }

    override suspend fun deletePlaylistById(id: Long) {
        database.withTransaction {
            playlistsDao.deletePlaylist(PlaylistEntity(
                id = id,
                name = "",
                description = ""
            ))
            tracksDao.deleteOrphanTracks()
        }
    }

    override suspend fun removeTrackFromPlaylist(trackId: Long, playlistId: Long) {
        database.withTransaction {
            playlistsDao.removeTrackFromPlaylist(
                PlaylistTrackCrossRefEntity(playlistId, trackId)
            )
            tracksDao.deleteOrphanTracks()
        }
    }

    override suspend fun removeAllTracksFromPlaylist(playlistId: Long) {
        database.withTransaction {
            playlistsDao.removeAllTracksFromPlaylist(playlistId)
            tracksDao.deleteOrphanTracks()
        }
    }
}