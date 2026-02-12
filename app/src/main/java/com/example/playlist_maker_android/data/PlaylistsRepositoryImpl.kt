package com.example.playlist_maker_android.data

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.compose.ui.platform.LocalGraphicsContext
import com.example.playlist_maker_android.data.database.AppDatabase
import com.example.playlist_maker_android.data.database.entity.PlaylistEntity
import com.example.playlist_maker_android.data.database.entity.toPlaylist
import com.example.playlist_maker_android.data.database.entity.toTrack
import com.example.playlist_maker_android.domain.PlaylistsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import com.example.playlist_maker_android.domain.Playlist
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
        val playlistFlow = playlistsDao.getPlaylistById(playlistId)
        val tracksFlow = tracksDao.getTracksForPlaylist(playlistId)

        return combine(playlistFlow, tracksFlow) { playlistEntity, trackEntities ->

            playlistEntity?.toPlaylist(
                trackEntities.map { it.toTrack() }
            )
        }
    }

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return combine(
            playlistsDao.getAllPlaylists(),
            tracksDao.getAllTracks()
        ) { playlistEntities, trackEntities ->

            playlistEntities.map { playlistEntity ->

                val tracks = trackEntities
                    .filter { it.playlistId == playlistEntity.id }
                    .map { it.toTrack() }

                playlistEntity.toPlaylist(tracks)
            }
        }
    }


    //    override suspend fun addNewPlaylist(name: String, description: String) {
    override suspend fun addNewPlaylist(playlist: Playlist) {
        try {
            playlistsDao.insertPlaylist(playlist.toEntity())
        } catch (e: SQLiteConstraintException) {
            Log.i("database", "Playlist с таким именем уже существует!")
        }
    }

    override suspend fun deletePlaylistById(id: Long) {
        playlistsDao.deletePlaylist(PlaylistEntity(
            id = id,
            name = "",
            description = ""
        ))
    }
}