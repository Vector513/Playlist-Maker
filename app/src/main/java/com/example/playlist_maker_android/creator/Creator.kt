package com.example.playlist_maker_android.creator

import com.example.playlist_maker_android.data.DatabaseMock
import com.example.playlist_maker_android.data.PlaylistsRepositoryImpl
import com.example.playlist_maker_android.data.network.TracksRepositoryImpl
import com.example.playlist_maker_android.domain.PlaylistsRepository
import com.example.playlist_maker_android.domain.TracksRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

object Creator {
    private val appScope: CoroutineScope by lazy {
        CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }

    private val database: DatabaseMock by lazy {
        DatabaseMock(scope = appScope)
    }

    private val tracksRepositoryInstance: TracksRepository by lazy {
        TracksRepositoryImpl(scope = appScope, database = database)
    }

    private val playlistsRepositoryInstance: PlaylistsRepository by lazy {
        PlaylistsRepositoryImpl(scope = appScope, database = database)
    }

    fun getTracksRepository(): TracksRepository = tracksRepositoryInstance
    fun getPlaylistsRepository(): PlaylistsRepository = playlistsRepositoryInstance
}