package com.example.playlist_maker_android.di

import android.content.Context
import androidx.room.Room
import com.example.playlist_maker_android.data.database.AppDatabase
import com.example.playlist_maker_android.data.database.MIGRATION_1_2
import com.example.playlist_maker_android.data.database.MIGRATION_2_3
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get<Context>(),
            AppDatabase::class.java,
            "playlists_maker"
        )
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
            .build()
    }
}