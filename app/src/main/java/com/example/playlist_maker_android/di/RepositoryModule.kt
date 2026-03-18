package com.example.playlist_maker_android.di

import com.example.playlist_maker_android.data.PlaylistsRepositoryImpl
import com.example.playlist_maker_android.data.SearchHistoryRepositoryImpl
import com.example.playlist_maker_android.data.TracksRepositoryImpl
import com.example.playlist_maker_android.domain.PlaylistsRepository
import com.example.playlist_maker_android.domain.SearchHistoryRepository
import com.example.playlist_maker_android.domain.TracksRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<TracksRepository> {
        TracksRepositoryImpl(networkClient = get(), database = get())
    }

    single<PlaylistsRepository> {
        PlaylistsRepositoryImpl(database = get())
    }

    single<SearchHistoryRepository> {
        SearchHistoryRepositoryImpl(searchHistoryPreferences = get())
    }
}
