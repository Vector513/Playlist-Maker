package com.example.playlist_maker_android

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.example.playlist_maker_android.di.dataModule
import com.example.playlist_maker_android.di.databaseModule
import com.example.playlist_maker_android.di.repositoryModule
import com.example.playlist_maker_android.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startKoin {
            androidContext(this@App)
            modules(databaseModule, dataModule, repositoryModule, viewModelModule)
        }
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            PLAYBACK_CHANNEL_ID,
            "Воспроизведение",
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = "Управление воспроизведением музыки"
            setShowBadge(false)
        }
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
    }

    companion object {
        const val PLAYBACK_CHANNEL_ID = "playback_channel"
    }
}