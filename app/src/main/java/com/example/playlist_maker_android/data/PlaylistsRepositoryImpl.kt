package com.example.playlist_maker_android.data

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.compose.ui.platform.LocalGraphicsContext
import androidx.room.Transaction
import com.example.playlist_maker_android.data.database.AppDatabase
import com.example.playlist_maker_android.data.database.entity.PlaylistEntity
import com.example.playlist_maker_android.data.database.entity.PlaylistTrackCrossRefEntity
import com.example.playlist_maker_android.data.database.entity.toPlaylist
import com.example.playlist_maker_android.data.database.entity.toTrack
import com.example.playlist_maker_android.data.database.toPlaylist
import com.example.playlist_maker_android.domain.PlaylistsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import com.example.playlist_maker_android.domain.Playlist
import com.example.playlist_maker_android.domain.Track
import com.example.playlist_maker_android.domain.toEntity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class PlaylistsRepositoryImpl(
    database: AppDatabase
) : PlaylistsRepository {
    private val playlistsDao = database.PlaylistsDao()
    private val tracksDao = database.TracksDao()

    override fun getPlaylist(playlistId: Long): Flow<Playlist?> {
        return playlistsDao.getPlaylistWithTracks(playlistId).map { it?.toPlaylist() }
    }

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return playlistsDao.getPlaylistsWithTracks().map { list -> list.map { it.toPlaylist() } }
    }

    override suspend fun addNewPlaylist(playlist: Playlist) {
        try {
            playlistsDao.insertPlaylist(playlist.toEntity())
        } catch (e: SQLiteConstraintException) {
            Log.i("database", "Playlist с таким именем уже существует!")
        }
    }

    @Transaction
    override suspend fun addTrackToPlaylist(track: Track, playlistId: Long) {
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

    @Transaction
    override suspend fun deletePlaylistById(id: Long) {
        playlistsDao.deletePlaylist(PlaylistEntity(
            id = id,
            name = "",
            description = ""
        ))
        tracksDao.deleteOrphanTracks()
    }

    @Transaction
    override suspend fun removeTrackFromPlaylist(trackId: Long, playlistId: Long) {
        playlistsDao.removeTrackFromPlaylist(
            PlaylistTrackCrossRefEntity(playlistId, trackId)
        )
        tracksDao.deleteOrphanTracks()
    }

    @Transaction
    override suspend fun removeAllTracksFromPlaylist(playlistId: Long) {
        playlistsDao.removeAllTracksFromPlaylist(playlistId)
        tracksDao.deleteOrphanTracks()
    }
}