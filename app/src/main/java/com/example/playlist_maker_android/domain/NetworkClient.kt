package com.example.playlist_maker_android.domain

interface NetworkClient {
    suspend fun search(dto: Any): BaseResponse

    suspend fun getTrackById(id: Long): BaseResponse
}