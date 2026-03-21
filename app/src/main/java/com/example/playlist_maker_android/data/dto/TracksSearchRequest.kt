package com.example.playlist_maker_android.data.dto

data class TracksSearchRequest(
    val expression: String,
    val limit: Int = DEFAULT_SEARCH_LIMIT
) {
    companion object {
        const val DEFAULT_SEARCH_LIMIT = 10
    }
}