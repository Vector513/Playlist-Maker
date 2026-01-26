package com.example.playlist_maker_android.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.playlist_maker_android.ui.search.components.SearchPanelHeader
import com.example.playlist_maker_android.ui.viewmodel.SearchViewModel
import com.example.playlist_maker_android.ui.search.components.SearchBar

@Composable
internal fun SearchScreen(
    viewModel: SearchViewModel = viewModel(
        factory = SearchViewModel.getViewModelFactory()
    ),
    onBack: () -> Unit
) {
    val screenState by viewModel.searchScreenState.collectAsState()
    val textState by viewModel.textFieldState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.primary)
                .padding(innerPadding)
            ) {

            SearchPanelHeader(onBack)

            SearchBar(
                textState = textState,
                onSearchClick = { viewModel.search(textState.text.toString()) },
                onClearClick = { viewModel.clearTextField() }
            )

            SearchContent(
                screenState = screenState
            )
        }
    }
}