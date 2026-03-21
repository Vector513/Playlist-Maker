package com.example.playlist_maker_android.domain

data class Track(
    val id: Long,
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    val image: String,
    val favorite: Boolean,
) {
    val highResImage: String
        get() = image.replace("100x100", "512x512")
}