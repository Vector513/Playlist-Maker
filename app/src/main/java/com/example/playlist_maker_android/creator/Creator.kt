package com.example.playlist_maker_android.creator

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.example.playlist_maker_android.data.ITunesApiService
import com.example.playlist_maker_android.data.PlaylistsRepositoryImpl
import com.example.playlist_maker_android.data.SearchHistoryRepositoryImpl
import com.example.playlist_maker_android.data.network.RetrofitNetworkClient
import com.example.playlist_maker_android.data.TracksRepositoryImpl
import com.example.playlist_maker_android.data.database.AppDatabase
import com.example.playlist_maker_android.data.database.MIGRATION_1_2
import com.example.playlist_maker_android.data.database.MIGRATION_2_3
import com.example.playlist_maker_android.data.preferences.SearchHistoryPreferences
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

    private lateinit var dataStore: DataStore<Preferences>

    private lateinit var database: AppDatabase

    fun init(context: Context) {
        if (!::database.isInitialized) {
            database = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "playlists_maker"
            ).addMigrations(
                MIGRATION_1_2,
                MIGRATION_2_3
            ).build()
        }

        dataStore = PreferenceDataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile("search_history_preferences")
            }
        )
    }

    private fun ensureDatabaseInitialized() {
        Log.i("init", "Checking for existing")
        if (!::database.isInitialized) {
            Log.i("init", "doesnot exist!!!")
            throw IllegalStateException("Creator.init(context) must be called before using repositories!")
        }
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
        ensureDatabaseInitialized()
        TracksRepositoryImpl(
            database = database,
            networkClient = networkClient
        )
    }

    private val playlistsRepositoryInstance: PlaylistsRepository by lazy {
        ensureDatabaseInitialized()
        PlaylistsRepositoryImpl(database = database)
    }

    private val searchHistoryRepositoryInstance: SearchHistoryRepository by lazy {
        SearchHistoryRepositoryImpl(
            SearchHistoryPreferences(
                dataStore,
                appScope
            )
        )
    }

    fun getTracksRepository(): TracksRepository = tracksRepositoryInstance
    fun getPlaylistsRepository(): PlaylistsRepository = playlistsRepositoryInstance
    fun getSearchHistoryRepository(): SearchHistoryRepository = searchHistoryRepositoryInstance
}
