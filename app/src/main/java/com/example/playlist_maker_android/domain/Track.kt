package com.example.playlist_maker_android.domain

import com.example.playlist_maker_android.data.database.entity.TrackEntity

data class Track(
    val id: Long,
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    val image: String,
    var favorite: Boolean,
)

fun Track.toEntity(
    favorite: Boolean = this.favorite
): TrackEntity {
    return TrackEntity(
        id = this.id,
        trackName = this.trackName,
        artistName = this.artistName,
        trackTime = this.trackTime,
        image = this.image,
        favorite = favorite
    )
}