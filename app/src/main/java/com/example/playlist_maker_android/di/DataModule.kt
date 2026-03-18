package com.example.playlist_maker_android.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.example.playlist_maker_android.data.ITunesApiService
import com.example.playlist_maker_android.data.network.NetworkClient
import com.example.playlist_maker_android.data.network.RetrofitNetworkClient
import com.example.playlist_maker_android.data.preferences.SearchHistoryPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single {
        CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }

    single {
        PreferenceDataStoreFactory.create(
            produceFile = {
                get<Context>().preferencesDataStoreFile("search_history_preferences")
            }
        )
    }

    single<ITunesApiService> {
        Retrofit.Builder()
            .baseUrl("https://itunes.apple.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ITunesApiService::class.java)
    }

    single<NetworkClient> {
        RetrofitNetworkClient(get())
    }

    single {
        SearchHistoryPreferences(get(), get())
    }
}
