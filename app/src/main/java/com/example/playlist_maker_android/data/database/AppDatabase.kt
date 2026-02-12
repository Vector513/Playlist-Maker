package com.example.playlist_maker_android.data.database

import androidx.room.Database
import com.example.playlist_maker_android.data.database.entity.TrackEntity
import com.example.playlist_maker_android.data.database.entity.PlaylistEntity
import androidx.room.RoomDatabase
import com.example.playlist_maker_android.data.database.dao.PlaylistsDao
import com.example.playlist_maker_android.data.database.dao.TracksDao

@Database(
    entities = [
        TrackEntity::class,
        PlaylistEntity::class
    ], version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun TracksDao(): TracksDao
    abstract fun PlaylistsDao(): PlaylistsDao
}