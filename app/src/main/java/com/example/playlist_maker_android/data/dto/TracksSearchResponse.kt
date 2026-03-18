package com.example.playlist_maker_android.data.dto

data class TracksSearchResponse(
    val resultCount: Int,
    val results: List<TrackDto>
) : BaseResponse()