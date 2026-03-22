package com.example.playlist_maker_android.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.setTextAndSelectAll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import com.example.playlist_maker_android.ui.search.components.SearchPanelHeader
import com.example.playlist_maker_android.ui.viewmodel.PlayerViewModel
import com.example.playlist_maker_android.ui.viewmodel.SearchViewModel
import org.koin.androidx.compose.koinViewModel
import com.example.playlist_maker_android.domain.QueueSource
import com.example.playlist_maker_android.domain.Track
import com.example.playlist_maker_android.ui.search.components.SearchBar
import com.example.playlist_maker_android.ui.viewmodel.SearchState

@Composable
internal fun SearchScreen(
    viewModel: SearchViewModel = koinViewModel(),
    playerViewModel: PlayerViewModel = koinViewModel(),
    onTrackClick: (Track) -> Unit,
    onBack: () -> Unit
) {
    val screenState by viewModel.searchScreenState.collectAsState()
    val textState by viewModel.textFieldState.collectAsState()
    val playerState by playerViewModel.playerState.collectAsState()

    val searchTracks = (screenState as? SearchState.Success)?.foundList ?: emptyList()

    LaunchedEffect(searchTracks) {
        if (playerState.currentTrack != null && playerState.queueSource is QueueSource.Search) {
            playerViewModel.updateQueue(searchTracks)
        }
    }

    var historyList by remember { mutableStateOf<List<String>>(emptyList()) }
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.primary)
                .padding(innerPadding)
            ) {

            LaunchedEffect(screenState) {
                when (screenState) {
                    is SearchState.Success -> {
                        focusManager.clearFocus()
                    }
                    else -> Unit
                }
            }

            LaunchedEffect(Unit) {
                historyList = viewModel.getHistoryList()
            }

            SearchPanelHeader(onBack)

            SearchBar(
                textState = textState,
                historyList = historyList,
                onSearchClick = { viewModel.search(textState.text.toString()) },
                onClearClick = { viewModel.clearTextField() },
                onHistoryClick = { word ->
                    textState.setTextAndSelectAll(word)
                    viewModel.search(textState.text.toString())
                }
            )

            SearchContent(
                screenState = screenState,
                text = textState.text,
                onTrackClick = { track ->
                    val playerState = playerViewModel.playerState.value
                    if (playerState.currentTrack?.id == track.id) {
                        playerViewModel.togglePlayPause()
                    } else {
                        val currentTracks = (screenState as? SearchState.Success)?.foundList
                            ?: emptyList()
                        playerViewModel.playFromQueue(currentTracks, track, QueueSource.Search)
                    }
                },
                onRetry = { viewModel.retrySearch() },
                onLoadMore = { viewModel.loadNextPage() },
                onNavigateClick = onTrackClick
            )
        }
    }
}