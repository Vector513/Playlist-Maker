package com.example.playlist_maker_android.data.database

import androidx.room.Database
import com.example.playlist_maker_android.data.database.entity.TrackEntity
import com.example.playlist_maker_android.data.database.entity.PlaylistEntity
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.playlist_maker_android.data.database.dao.PlaylistsDao
import com.example.playlist_maker_android.data.database.dao.TracksDao
import com.example.playlist_maker_android.data.database.entity.PlaylistTrackCrossRefEntity

@Database(
    entities = [
        TrackEntity::class,
        PlaylistEntity::class,
        PlaylistTrackCrossRefEntity::class
    ], version = 2, exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun TracksDao(): TracksDao
    abstract fun PlaylistsDao(): PlaylistsDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {

        db.execSQL("""
            CREATE TABLE tracks_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                trackName TEXT NOT NULL,
                artistName TEXT NOT NULL,
                trackTime TEXT NOT NULL,
                image TEXT NOT NULL,
                favorite INTEGER NOT NULL DEFAULT 0
            )
        """.trimIndent())

        db.execSQL("""
            INSERT INTO tracks_new (id, trackName, artistName, trackTime, image, favorite)
            SELECT id, trackName, artistName, trackTime, image, favorite
            FROM tracks
        """.trimIndent())

        db.execSQL("DROP TABLE tracks")

        db.execSQL("ALTER TABLE tracks_new RENAME TO tracks")

        db.execSQL("""
            CREATE TABLE IF NOT EXISTS playlist_track_cross_ref (
                playlistId INTEGER NOT NULL,
                trackId INTEGER NOT NULL,
                PRIMARY KEY(playlistId, trackId),
                FOREIGN KEY(playlistId) REFERENCES playlists(id) ON DELETE CASCADE,
                FOREIGN KEY(trackId) REFERENCES tracks(id) ON DELETE CASCADE
            )
        """.trimIndent())
    }
}