package com.example.playlist_maker_android

import android.app.Application
import com.example.playlist_maker_android.creator.Creator

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Creator.init(this)
    }
}