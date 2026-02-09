package com.example.playlist_maker_android.creator

import com.example.playlist_maker_android.data.DatabaseMock
import com.example.playlist_maker_android.data.ITunesApiService
import com.example.playlist_maker_android.data.PlaylistsRepositoryImpl
import com.example.playlist_maker_android.data.SearchHistoryRepositoryImpl
import com.example.playlist_maker_android.data.network.RetrofitNetworkClient
import com.example.playlist_maker_android.data.network.TracksRepositoryImpl
import com.example.playlist_maker_android.domain.PlaylistsRepository
import com.example.playlist_maker_android.domain.SearchHistoryRepository
import com.example.playlist_maker_android.domain.TracksRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Creator {
    private val appScope: CoroutineScope by lazy {
        CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }

    private val database: DatabaseMock by lazy {
        DatabaseMock(scope = appScope)
    }

    private val networkClient: RetrofitNetworkClient by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://itunes.apple.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(ITunesApiService::class.java)
        RetrofitNetworkClient(api)
    }

    private val tracksRepositoryInstance: TracksRepository by lazy {
        TracksRepositoryImpl(
            database = database,
            networkClient = networkClient
        )
    }

    private val playlistsRepositoryInstance: PlaylistsRepository by lazy {
        PlaylistsRepositoryImpl(scope = appScope, database = database)
    }

    private val searchHistoryRepositoryInstance: SearchHistoryRepository by lazy {
        SearchHistoryRepositoryImpl(database = database)
    }

    fun getTracksRepository(): TracksRepository = tracksRepositoryInstance
    fun getPlaylistsRepository(): PlaylistsRepository = playlistsRepositoryInstance
    fun getSearchHistoryRepository(): SearchHistoryRepository = searchHistoryRepositoryInstance
}
