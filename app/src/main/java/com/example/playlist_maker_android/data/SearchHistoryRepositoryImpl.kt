package com.example.playlist_maker_android.data

import com.example.playlist_maker_android.domain.SearchHistoryRepository
import com.example.playlist_maker_android.domain.Word
import kotlinx.coroutines.CoroutineScope

class SearchHistoryRepositoryImpl(
    private val database: DatabaseMock
): SearchHistoryRepository {

    override suspend fun getHistoryRequests(): List<Word> {
        return database.getHistoryRequests()
    }

    override fun addToHistory(word: Word) {
        database.addToHistory(word = word)
    }
}