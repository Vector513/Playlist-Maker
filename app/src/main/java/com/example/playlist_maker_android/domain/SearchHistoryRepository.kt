package com.example.playlist_maker_android.domain

interface SearchHistoryRepository {

    suspend fun getHistoryRequests(): List<String>

    suspend fun addToHistory(entry: String)
}