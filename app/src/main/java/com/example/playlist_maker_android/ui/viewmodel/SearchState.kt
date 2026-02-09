package com.example.playlist_maker_android.ui.viewmodel

import com.example.playlist_maker_android.domain.Track

sealed class SearchState {
    object Initial: SearchState()
    object Searching: SearchState()
    data class Success(val foundList: List<Track>): SearchState()
    data class Fail(val error: String): SearchState()
}
