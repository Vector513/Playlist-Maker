package com.example.playlist_maker_android.creator

import com.example.playlist_maker_android.data.network.RetrofitNetworkClient
import com.example.playlist_maker_android.data.network.TracksRepositoryImpl
import com.example.playlist_maker_android.domain.TracksRepository

object Creator {
    fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl()
    }
}