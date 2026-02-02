package com.example.playlist_maker_android.domain

import com.example.playlist_maker_android.data.Word

interface SearchHistoryRepository {

    suspend fun getHistoryRequests(): List<Word>

    fun addToHistory(word: Word)
}