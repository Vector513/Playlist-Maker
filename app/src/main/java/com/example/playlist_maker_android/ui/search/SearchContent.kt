package com.example.playlist_maker_android.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.playlist_maker_android.ui.viewmodel.SearchState
import com.example.playlist_maker_android.domain.Track

@Composable
fun SearchContent(
    screenState: SearchState,
    text: CharSequence,
    onTrackClick: (Track) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (screenState) {
            is SearchState.Initial -> SearchEmptyState(text)
            is SearchState.Searching -> SearchLoadingState()
            is SearchState.Success -> SearchResultsState(screenState.foundList, onTrackClick)
            is SearchState.Fail -> SearchErrorState(screenState.error)
        }
    }
}