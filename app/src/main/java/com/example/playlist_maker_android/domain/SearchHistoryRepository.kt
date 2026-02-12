package com.example.playlist_maker_android.domain

interface SearchHistoryRepository {

    suspend fun getHistoryRequests(): List<Word>

    suspend fun addToHistory(word: Word)
}