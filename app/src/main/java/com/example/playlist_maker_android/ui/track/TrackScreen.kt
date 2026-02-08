package com.example.playlist_maker_android.ui.track

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import com.example.playlist_maker_android.ui.search.components.ArrowBackButton
import com.example.playlist_maker_android.ui.theme.Dimensions
import com.example.playlist_maker_android.R
import com.example.playlist_maker_android.ui.playlists.components.PlaylistListItem
import com.example.playlist_maker_android.ui.viewmodel.PlaylistsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackScreen(
    trackId: Long,
    playlistsViewModel: PlaylistsViewModel,
    onBack: () -> Unit
) {
    LaunchedEffect(trackId) {
        playlistsViewModel.loadTrack(trackId)
    }

    val trackState by playlistsViewModel.currentTrack.collectAsState()
    val playlists by playlistsViewModel.playlists.collectAsState(emptyList())

    var isFavorite by remember(trackId) { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }

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
                    ArrowBackButton(onBack)
                }
            }

            Spacer(modifier = Modifier.height(26.dp))

            if (trackState != null) {
                val track = trackState!!

                LaunchedEffect(track.id, track.favorite) {
                    isFavorite = track.favorite
                }

                // TODO: replace with image
                Spacer(modifier = Modifier.height(312.dp))

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = track.trackName,
                    modifier = Modifier.padding(start = 24.dp),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = track.artistName,
                    modifier = Modifier.padding(start = 24.dp),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                )

                Spacer(modifier = Modifier.height(54.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(24.dp))

                    IconButton(onClick = {
                        showBottomSheet = true
                    },
                        modifier = Modifier.size(51.dp),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.onPrimary.copy(
                                alpha = 0.25f
                            )
                        )
                    ) {
                        Icon(
                            painter = painterResource(
                                id = R.drawable.ic_add_to_playlist
                            ),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(
                        onClick = {
                            val newValue = !isFavorite
                            isFavorite = newValue
                            playlistsViewModel.toggleFavorite(track, newValue)
                        },
                        modifier = Modifier.size(51.dp),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.25f)
                        )
                    ) {
                        Icon(
                            painter = painterResource(
                                id = if (isFavorite)
                                    R.drawable.ic_add_to_favourites_filled
                                else
                                    R.drawable.ic_add_to_favourites_outline
                            ),
                            contentDescription = null,
                            tint = if (isFavorite)
                                Color.Red
                            else
                                MaterialTheme.colorScheme.onBackground
                        )
                    }


                    Spacer(modifier = Modifier.width(24.dp))
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = stringResource(R.string.track_time_text),
                        style = MaterialTheme.typography.displaySmall.copy(
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = track.trackTime,
                        style = MaterialTheme.typography.displaySmall.copy(
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    )

                    Spacer(modifier = Modifier.width(16.dp))
                }

                if (showBottomSheet) {
                    val sheetState = rememberModalBottomSheetState()

                    ModalBottomSheet(
                        onDismissRequest = { showBottomSheet = false },
                        modifier = Modifier.heightIn(505.dp),
                        sheetState = sheetState
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = stringResource(R.string.add_to_playlist_text),
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            LazyColumn {
                                items(playlists) { playlist ->
                                    PlaylistListItem(playlist) {
                                        playlistsViewModel.insertTrackToPlaylist(
                                            track,
                                            playlist.id
                                        )
                                        showBottomSheet = false
                                    }

//                                    Row(
//                                        modifier = Modifier
//                                            .fillMaxWidth()
//                                            .padding(vertical = 8.dp)
//                                            .clickable {
//                                                playlistsViewModel.insertTrackToPlaylist(
//                                                    track,
//                                                    playlist.id
//                                                )
//                                                showBottomSheet = false
//                                            },
//                                        verticalAlignment = Alignment.CenterVertically
//                                    ) {
//                                        Text(
//                                            text = playlist.name,
//                                            style = MaterialTheme.typography.bodyLarge
//                                        )
//                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                Text(
                    text = "Трек не найден",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        }
    }
}