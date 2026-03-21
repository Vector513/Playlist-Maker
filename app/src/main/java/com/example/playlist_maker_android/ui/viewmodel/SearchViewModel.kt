package com.example.playlist_maker_android.ui.viewmodel

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker_android.domain.ServerErrorException
import com.example.playlist_maker_android.domain.Track
import com.example.playlist_maker_android.domain.TracksRepository
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
    val searchScreenState = _searchScreenState.asStateFlow()

    private val _textFieldState = MutableStateFlow(TextFieldState(""))
    val textFieldState = _textFieldState.asStateFlow()

    private var currentQuery = ""
    private var cachedTracks = emptyList<Track>()
    private var visibleCount = 0

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
        currentQuery = request
        cachedTracks = emptyList()
        visibleCount = 0

        viewModelScope.launch(Dispatchers.IO) {
            try {
                _searchScreenState.update { SearchState.Searching }
                searchHistoryRepository.addToHistory(request)
                cachedTracks = tracksRepository.searchTracks(
                    expression = request,
                    limit = API_FETCH_LIMIT
                )
                visibleCount = minOf(PAGE_SIZE, cachedTracks.size)
                _searchScreenState.update {
                    SearchState.Success(
                        foundList = cachedTracks.take(visibleCount),
                        canLoadMore = visibleCount < cachedTracks.size
                    )
                }
            } catch (e: ServerErrorException) {
                _searchScreenState.update { SearchState.ServerError }
            } catch (e: IOException) {
                _searchScreenState.update { SearchState.Fail("Проблемы с сетью: ${e.message ?: "Неизвестная ошибка"}") }
            } catch (e: Exception) {
                _searchScreenState.update { SearchState.Fail("Не удалось обработать ответ сервера: ${e.message ?: "Неизвестная ошибка"}") }
            }
        }
    }

    fun loadNextPage() {
        val current = _searchScreenState.value
        if (current !is SearchState.Success || !current.canLoadMore) return

        visibleCount = minOf(visibleCount + PAGE_SIZE, cachedTracks.size)
        _searchScreenState.value = SearchState.Success(
            foundList = cachedTracks.take(visibleCount),
            canLoadMore = visibleCount < cachedTracks.size
        )
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
        currentQuery = ""
        cachedTracks = emptyList()
        visibleCount = 0
    }

    suspend fun getHistoryList() = searchHistoryRepository.getHistoryRequests()

    companion object {
        private const val API_FETCH_LIMIT = 200
        private const val PAGE_SIZE = 10
    }
}
