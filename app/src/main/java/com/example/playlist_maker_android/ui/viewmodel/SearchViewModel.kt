package com.example.playlist_maker_android.ui.viewmodel

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker_android.creator.Creator
import com.example.playlist_maker_android.domain.ServerErrorException
import com.example.playlist_maker_android.domain.TracksRepository
import com.example.playlist_maker_android.domain.Word
import com.example.playlist_maker_android.domain.SearchHistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val tracksRepository: TracksRepository,
    private val searchHistoryRepository: SearchHistoryRepository
) : ViewModel() {
    private val _searchScreenState = MutableStateFlow<SearchState>(SearchState.Initial)
    val searchScreenState  = _searchScreenState.asStateFlow()

    private val _textFieldState = MutableStateFlow<TextFieldState>(TextFieldState(""))
    val textFieldState = _textFieldState.asStateFlow()

    init {
        viewModelScope.launch {
            _textFieldState
                .debounce(1000)
                .distinctUntilChanged()
                .collect { query ->
                    if (query.text.isNotEmpty()) {
                        search(query.text.toString())
                    }
                }
        }
    }

    fun search(request: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _searchScreenState.update { SearchState.Searching }
                searchHistoryRepository.addToHistory(Word(word = request))
                val list = tracksRepository.searchTracks(expression = request)
                _searchScreenState.update { SearchState.Success(foundList = list) }
            } catch (e: ServerErrorException) {
                _searchScreenState.update { SearchState.ServerError }
            } catch (e: IOException) {
                _searchScreenState.update { SearchState.Fail("Проблемы с сетью: ${e.message ?: "Неизвестная ошибка"}") }
            } catch (e: Exception) {
                _searchScreenState.update { SearchState.Fail("Не удалось обработать ответ сервера: ${e.message ?: "Неизвестная ошибка"}") }
            }
        }
    }
    
    fun retrySearch() {
        val currentText = _textFieldState.value.text.toString()
        if (currentText.isNotEmpty()) {
            search(currentText)
        }
    }

    fun clearTextField() {
        _textFieldState.update { TextFieldState("") }
        _searchScreenState.update { SearchState.Initial }
    }

    suspend fun getHistoryList() = searchHistoryRepository.getHistoryRequests()

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return SearchViewModel(
                    Creator.getTracksRepository(),
                    Creator.getSearchHistoryRepository()
                ) as T
                }
            }
    }
}