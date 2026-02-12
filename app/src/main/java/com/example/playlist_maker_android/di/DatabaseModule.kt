package com.example.playlist_maker_android.di

import android.content.Context
import androidx.room.Room
import com.example.playlist_maker_android.data.database.AppDatabase
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get<Context>(),
            AppDatabase::class.java,
            "playlists_maker"
        ).build()
    }
}