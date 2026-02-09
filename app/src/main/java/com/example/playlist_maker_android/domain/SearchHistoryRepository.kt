package com.example.playlist_maker_android.domain

interface SearchHistoryRepository {

    suspend fun getHistoryRequests(): List<Word>

    fun addToHistory(word: Word)
}