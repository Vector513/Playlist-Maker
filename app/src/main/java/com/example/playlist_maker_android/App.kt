package com.example.playlist_maker_android

import android.app.Application
import com.example.playlist_maker_android.di.dataModule
import com.example.playlist_maker_android.di.databaseModule
import com.example.playlist_maker_android.di.repositoryModule
import com.example.playlist_maker_android.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(databaseModule, dataModule, repositoryModule, viewModelModule)
        }
    }
}