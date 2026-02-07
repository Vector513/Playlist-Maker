package com.example.playlist_maker_android.ui.playlists

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlist_maker_android.R
import com.example.playlist_maker_android.ui.playlists.components.PlaylistListItem
import com.example.playlist_maker_android.ui.search.components.ArrowBackButton
import com.example.playlist_maker_android.ui.theme.Dimensions
import com.example.playlist_maker_android.ui.viewmodel.PlaylistsViewModel

@Composable
fun PlaylistsScreen(
    playlistsViewModel: PlaylistsViewModel,
    addNewPlaylist: () -> Unit,
    navigateBack: () -> Unit,
    navigateToPlaylist: (Int) -> Unit
) {
    val playlists by playlistsViewModel.playlists.collectAsState(emptyList())

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimensions.PanelHeaderHeight)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = 4.dp, top = 4.dp, bottom = 4.dp)
                ) {
                    ArrowBackButton(navigateBack)
                    Text(
                        text = stringResource(R.string.playlists_panel_header_name),
                        modifier = Modifier
                            .padding(start = 12.dp),
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.onPrimary,
                            textAlign = TextAlign.Center
                        ),
                    )
                }
            }

            Box {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(playlists.size) { index ->
                        PlaylistListItem(playlist = playlists[index]) {
                            navigateToPlaylist(index)
                        }
                    }
                }

                FloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 16.dp, bottom = 16.dp)
                        .size(51.dp),
                    onClick = { addNewPlaylist() },
                    containerColor = MaterialTheme.colorScheme.onPrimary.copy(
                        alpha = 0.25f
                    ),
                    contentColor = MaterialTheme.colorScheme.onBackground,
                    shape = CircleShape,
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp,
                        focusedElevation = 0.dp,
                        hoveredElevation = 0.dp
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_create_new_playlist),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
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