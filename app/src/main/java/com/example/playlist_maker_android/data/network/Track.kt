package com.example.playlist_maker_android.data.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Track(
    val id: Long,
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    val image: String,
    var favorite: Boolean,
    var playlistId: Long
) : Parcelable