package com.example.playlist_maker_android.data.mapper

import com.example.playlist_maker_android.data.dto.TrackDto
import com.example.playlist_maker_android.domain.Track

object TrackMapper {

    fun fromDto(dto: TrackDto, favorite: Boolean = false, playlistId: Long = 0): Track =
        Track(
            id = dto.id,
            trackName = dto.trackName,
            artistName = dto.artistName,
            trackTime = formatTrackTime(dto.trackTimeMillis),
            image = dto.image ?: "",
            favorite = favorite,
            playlistId = playlistId
        )

    private fun formatTrackTime(millis: Long): String {
        val totalSeconds = millis / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return "%d:%02d".format(minutes, seconds)
    }
}
