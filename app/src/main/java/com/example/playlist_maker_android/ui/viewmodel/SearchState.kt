package com.example.playlist_maker_android.ui.viewmodel

import com.example.playlist_maker_android.domain.Track

sealed class SearchState {
    data object Initial: SearchState()
    data object Searching: SearchState()
    data class Success(
        val foundList: List<Track>,
        val canLoadMore: Boolean = true
    ): SearchState()
    data class Fail(val error: String): SearchState()
    data object ServerError: SearchState()
}
