package com.example.playlist_maker_android.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.playlist_maker_android.ui.viewmodel.SearchState

@Composable
fun SearchContent(
    screenState: SearchState
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (screenState) {
            is SearchState.Initial -> SearchEmptyState()
            is SearchState.Searching -> SearchLoadingState()
            is SearchState.Success -> SearchResultsState(screenState.foundList)
            is SearchState.Fail -> SearchErrorState(screenState.error)
        }
    }
}