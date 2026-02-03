package com.example.playlist_maker_android.ui.playlists

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlist_maker_android.R
import com.example.playlist_maker_android.ui.playlists.components.PlaylistListItem
import com.example.playlist_maker_android.ui.viewmodel.PlaylistsViewModel

@Composable
fun PlaylistsScreen(
    playlistsViewModel: PlaylistsViewModel = PlaylistsViewModel(),
    addNewPlaylist: () -> Unit,
    navigateBack: () -> Unit,
    navigateToPlaylist: (Int) -> Unit
) {
    val playlists by playlistsViewModel.playlists.collectAsState(emptyList())

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray.copy(alpha = 0.7f)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { navigateBack() },
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null // stringResource(R.string.search_icon)
                )
                Text("Playlists", fontSize = 32.sp)
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, start = 8.dp, end = 8.dp),
            ) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(playlists.size) { index ->
                        PlaylistListItem(playlist = playlists[index]) {
                            navigateToPlaylist(index)
                        }
                        HorizontalDivider(thickness = 0.5.dp)
                    }
                }
            }
        }
        FloatingActionButton(
            modifier = Modifier
                .padding(32.dp)
                .align(Alignment.BottomEnd),
            onClick = { addNewPlaylist() },
            containerColor = Color.Gray,
            contentColor = Color.White,
            shape = CircleShape
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null // stringResource(R.string.add_playlist)
            )
        }
    }
}

//@Composable
//fun PlaylistsScreen(
//    onBack: () -> Unit
//) {
//    var showBottomSheet  by remember { mutableStateOf(false) }
//    Scaffold(
//        modifier = Modifier.fillMaxSize()
//    ) { innerPadding ->
//        FloatButtonExample(Modifier) { showBottomSheet = true }
//
//        BottomSheetExample(
//            modifier = Modifier.padding(innerPadding),
//            isShowPanel = showBottomSheet,
//            onDismissRequest = { showBottomSheet  = false },
//            content = "Это панель BottomSheet"
//        )
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetExample(
    modifier: Modifier,
    isShowPanel: Boolean,
    onDismissRequest: () -> Unit,
    content: String
) {
    val sheetState = rememberModalBottomSheetState()

    if (isShowPanel) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = content,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Icon(
                    modifier = Modifier
                        .size(32.dp)
                        .padding(top = 16.dp),
                    imageVector = Icons.Default.Home,
                    contentDescription = "Icon"
                )
            }
        }
    }
}

@Composable
fun FloatButtonExample(
    modifier: Modifier,
    callback: () -> Unit
) {
    Box (modifier = modifier.fillMaxSize()) {
        FloatingActionButton(
            modifier = Modifier
                .padding(32.dp)
                .align(Alignment.BottomEnd),
            onClick = callback,
            containerColor = Color.Gray,
            contentColor = Color.White,
            shape = CircleShape
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "This is FloatingActionButton"
            )
        }
    }
}