package com.example.playlist_maker_android.di

import com.example.playlist_maker_android.data.MediaPlayerRepository
import com.example.playlist_maker_android.data.PlaylistsRepositoryImpl
import com.example.playlist_maker_android.data.SearchHistoryRepositoryImpl
import com.example.playlist_maker_android.data.TracksRepositoryImpl
import com.example.playlist_maker_android.data.cache.PreviewCacheManager
import com.example.playlist_maker_android.domain.PlayerRepository
import com.example.playlist_maker_android.domain.PlaylistsRepository
import com.example.playlist_maker_android.domain.SearchHistoryRepository
import com.example.playlist_maker_android.domain.TracksRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { PreviewCacheManager(get()) }

    single<TracksRepository> {
        TracksRepositoryImpl(networkClient = get(), database = get(), cacheManager = get())
    }

    single<PlaylistsRepository> {
        PlaylistsRepositoryImpl(database = get(), cacheManager = get())
    }

    single<SearchHistoryRepository> {
        SearchHistoryRepositoryImpl(searchHistoryPreferences = get())
    }

    single<PlayerRepository> {
        MediaPlayerRepository(cacheManager = get())
    }
}
