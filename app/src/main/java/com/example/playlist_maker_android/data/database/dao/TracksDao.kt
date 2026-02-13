package com.example.playlist_maker_android.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.playlist_maker_android.data.database.entity.PlaylistEntity
import com.example.playlist_maker_android.data.database.entity.TrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TracksDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertTrack(track: TrackEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM tracks WHERE id = :trackId)")
    suspend fun exists(trackId: Long): Boolean

    @Query("SELECT * FROM tracks")
    fun getAllTracks(): Flow<List<TrackEntity>>

    @Query("SELECT * FROM tracks WHERE id = :id")
    fun getTrackById(id: Long): Flow<TrackEntity?>

    @Query("SELECT * FROM tracks WHERE favorite = :favorite")
    fun getTracksForFavorites(favorite: Boolean = true): Flow<List<TrackEntity>>

    @Query("SELECT * FROM tracks WHERE trackName = :name AND artistName = :artist")
    fun getTrackByNameAndArtist(name: String, artist: String): Flow<TrackEntity?>

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateTrack(trackEntity: TrackEntity)

    @Delete
    suspend fun deleteTrack(trackEntity: TrackEntity)

    // wrong
    @Query("SELECT COUNT(*) FROM playlist_track_cross_ref WHERE trackId = :trackId")
    suspend fun getPlaylistsCountForTrack(trackId: Long): Int

    @Query("""
        DELETE FROM tracks
        WHERE favorite = 0
          AND id NOT IN (
              SELECT trackId
              FROM playlist_track_cross_ref
              WHERE trackId IS NOT NULL
          )
""")
    suspend fun deleteOrphanTracks()
}