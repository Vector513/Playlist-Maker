package com.example.playlist_maker_android.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.playlist_maker_android.data.database.entity.PlaylistEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistsDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertPlaylist(playlist: PlaylistEntity)

    @Query("SELECT * FROM playlists")
    fun getAllPlaylists(): Flow<List<PlaylistEntity>>

    @Query("SELECT * FROM playlists WHERE id = :id")
    fun getPlaylistById(id: Long): Flow<PlaylistEntity?>

    @Query("SELECT * FROM playlists WHERE name = :name")
    fun getPlaylistByName(name: String): Flow<PlaylistEntity?>

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun updatePlaylist(playlistEntity: PlaylistEntity)

    @Delete
    fun deletePlaylist(playlistEntity: PlaylistEntity)
}