package com.example.playlist_maker_android.data

import androidx.datastore.core.DataStore
import com.example.playlist_maker_android.data.preferences.SearchHistoryPreferences
import com.example.playlist_maker_android.domain.SearchHistoryRepository
import com.example.playlist_maker_android.domain.Word
import kotlinx.coroutines.CoroutineScope

class SearchHistoryRepositoryImpl(
    private val searchHistoryPreferences: SearchHistoryPreferences
): SearchHistoryRepository {

    override suspend fun getHistoryRequests(): List<Word> {
        return searchHistoryPreferences.getEntries().map { Word(it) }
    }

    override suspend fun addToHistory(word: Word) {
        searchHistoryPreferences.addEntry(word.word)
    }
}