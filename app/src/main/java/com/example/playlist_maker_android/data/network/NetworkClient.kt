package com.example.playlist_maker_android.data.network

import com.example.playlist_maker_android.domain.BaseResponse

interface NetworkClient {
    suspend fun search(dto: Any): BaseResponse

    suspend fun getTrackById(id: Long): BaseResponse
}