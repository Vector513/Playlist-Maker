package com.example.playlist_maker_android.domain

import com.example.playlist_maker_android.data.dto.BaseResponse

interface NetworkClient {
    suspend fun doRequest(dto: Any): BaseResponse
}