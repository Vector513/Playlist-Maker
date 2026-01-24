package com.example.playlist_maker_android.data.network

import com.example.playlist_maker_android.creator.Storage
import com.example.playlist_maker_android.domain.NetworkClient
import com.example.playlist_maker_android.data.dto.TracksSearchRequest
import com.example.playlist_maker_android.data.dto.TracksSearchResponse

class RetrofitNetworkClient(private val storage: Storage) : NetworkClient {

    override fun doRequest(dto: Any): TracksSearchResponse {
        val searchList = storage.search((dto as TracksSearchRequest).expression)
        return TracksSearchResponse(searchList).apply { resultCode = 200 }
    }
}