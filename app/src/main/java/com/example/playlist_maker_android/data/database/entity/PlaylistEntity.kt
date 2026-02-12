package com.example.playlist_maker_android.data.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.playlist_maker_android.domain.Playlist
import com.example.playlist_maker_android.domain.Track

@Entity(
    tableName = "playlists",
    indices = [Index(value = ["name"], unique = true)]
)
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val description: String
)

fun PlaylistEntity.toPlaylist(tracks: List<Track>): Playlist {
    return Playlist(
        id = this.id,
        name = this.name,
        description = this.description,
        tracks = tracks
    )
}