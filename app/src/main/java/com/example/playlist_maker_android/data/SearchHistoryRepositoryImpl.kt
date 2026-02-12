package com.example.playlist_maker_android.data

import androidx.datastore.core.DataStore
import com.example.playlist_maker_android.data.preferences.SearchHistoryPreferences
import com.example.playlist_maker_android.domain.SearchHistoryRepository
import kotlinx.coroutines.CoroutineScope

class SearchHistoryRepositoryImpl(
    private val searchHistoryPreferences: SearchHistoryPreferences
): SearchHistoryRepository {

    override suspend fun getHistoryRequests(): List<String> {
        return searchHistoryPreferences.getEntries()
    }

    override suspend fun addToHistory(entry: String) {
        searchHistoryPreferences.addEntry(entry)
    }
}