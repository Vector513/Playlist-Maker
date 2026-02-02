package com.example.playlist_maker_android.data

import com.example.playlist_maker_android.domain.SearchHistoryRepository
import kotlinx.coroutines.CoroutineScope

class SearchHistoryRepositoryImpl(private val scope: CoroutineScope): SearchHistoryRepository {
    private val database = DatabaseMock(scope = scope)

    override suspend fun getHistoryRequests(): List<Word> {
        return database.getHistoryRequests()
    }

    override fun addToHistory(word: Word) {
        database.addToHistory(word = word)
    }
}