package com.example.playlist_maker_android.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class DatabaseMock(val scope: CoroutineScope) {

    private val historyList = mutableListOf<Word>(
        Word("Beatles"),
        Word("Группа"),
        Word("Yesterday"),
        Word("Let it Be"))
    private val _historyUpdates = MutableSharedFlow<Unit>()

    fun getHistoryRequests(): List<Word> = historyList.reversed()

    fun notifyHistoryChanged() {
        scope.launch(Dispatchers.IO) {
            _historyUpdates.emit(Unit)
        }
    }

    fun addToHistory(word: Word) {
        historyList.add(word)
        notifyHistoryChanged()
    }
}