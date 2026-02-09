package com.example.playlist_maker_android.data.dto

import com.example.playlist_maker_android.domain.BaseResponse

data class TracksSearchResponse(
    val resultCount: Int,
    val results: List<TrackDto>
) : BaseResponse()