package com.example.playlist_maker_android.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.playlist_maker_android.domain.Track

@Entity(tableName = "tracks")
data class TrackEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    val image: String,
    val favorite: Boolean = false,
    val playlistId: Long
)

fun TrackEntity.toTrack(): Track {
    return Track(
        id = this.id,
        trackName = this.trackName,
        artistName = this.artistName,
        trackTime = this.trackTime,
        favorite = this.favorite,
        image = this.image,
        playlistId = this.playlistId
    )
}