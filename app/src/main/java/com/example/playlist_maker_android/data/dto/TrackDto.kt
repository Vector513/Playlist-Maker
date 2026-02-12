package com.example.playlist_maker_android.data.dto

import com.example.playlist_maker_android.domain.Track
import com.google.gson.annotations.SerializedName

data class TrackDto(
    @SerializedName("trackId") val id: Long,
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Long,
    val previewUrl: String?,
    @SerializedName("artworkUrl100") val image: String?
)

fun TrackDto.toTrack(favorite: Boolean = false, playlistId: Long = 0): Track =
    Track(
        id = this.id,
        trackName = this.trackName,
        artistName = this.artistName,
        trackTime = formatTrackTime(this.trackTimeMillis),
        image = this.image ?: "",
        favorite = favorite,
        playlistId = playlistId
    )

private fun formatTrackTime(millis: Long): String {
    val totalSeconds = millis / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%d:%02d".format(minutes, seconds)
}