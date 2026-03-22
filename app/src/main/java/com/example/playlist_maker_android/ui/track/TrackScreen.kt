package com.example.playlist_maker_android.ui.track

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.playlist_maker_android.ui.search.components.ArrowBackButton
import com.example.playlist_maker_android.ui.theme.Dimensions
import com.example.playlist_maker_android.R
import com.example.playlist_maker_android.ui.playlists.components.PlaylistListItem
import com.example.playlist_maker_android.ui.viewmodel.PlayerViewModel
import com.example.playlist_maker_android.ui.viewmodel.TrackViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackScreen(
    viewModel: TrackViewModel,
    onBack: () -> Unit,
    playerViewModel: PlayerViewModel = koinViewModel()
) {
    val trackState by viewModel.currentTrack.collectAsState()
    val playlists by viewModel.playlists.collectAsState(emptyList())
    val playerState by playerViewModel.playerState.collectAsState()


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

            trackState?.let { track ->

                AsyncImage(
                    model = track.highResImage,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

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

                Spacer(modifier = Modifier.height(24.dp))

                if (track.previewUrl != null) {
                    val isCurrentTrack = playerState.currentTrack?.id == track.id
                    val progress = if (isCurrentTrack && playerState.durationMs > 0) {
                        playerState.currentPositionMs.toFloat() / playerState.durationMs
                    } else 0f
                    val currentMs = if (isCurrentTrack) playerState.currentPositionMs else 0
                    val durationMs = if (isCurrentTrack) playerState.durationMs else 0

                    Slider(
                        value = progress,
                        onValueChange = { newValue ->
                            if (isCurrentTrack) {
                                playerViewModel.seekTo((newValue * playerState.durationMs).toInt())
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        colors = SliderDefaults.colors(
                            thumbColor = MaterialTheme.colorScheme.onPrimary,
                            activeTrackColor = MaterialTheme.colorScheme.onPrimary,
                            inactiveTrackColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f)
                        )
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    ) {
                        Text(
                            text = formatMs(currentMs),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                        Spacer(Modifier.weight(1f))
                        Text(
                            text = formatMs(durationMs),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = { showBottomSheet = true },
                            modifier = Modifier.size(Dimensions.PlayerControlSize),
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.25f)
                            )
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_add_to_playlist),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }

                        IconButton(
                            onClick = {
                                if (isCurrentTrack) {
                                    playerViewModel.togglePlayPause()
                                } else {
                                    playerViewModel.playTrack(track)
                                }
                            },
                            modifier = Modifier.size(64.dp),
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.25f)
                            )
                        ) {
                            Icon(
                                painter = painterResource(
                                    if (isCurrentTrack && playerState.isPlaying) R.drawable.ic_pause
                                    else R.drawable.ic_play
                                ),
                                contentDescription = stringResource(
                                    if (isCurrentTrack && playerState.isPlaying) R.string.pause_description
                                    else R.string.play_description
                                ),
                                modifier = Modifier.size(32.dp),
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }

                        IconButton(
                            onClick = { viewModel.toggleFavorite(!track.favorite) },
                            modifier = Modifier.size(Dimensions.PlayerControlSize),
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.25f)
                            )
                        ) {
                            Icon(
                                painter = painterResource(
                                    if (track.favorite) R.drawable.ic_add_to_favourites_filled
                                    else R.drawable.ic_add_to_favourites_outline
                                ),
                                contentDescription = null,
                                tint = if (track.favorite) Color.Red
                                else MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                } else {
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = { showBottomSheet = true },
                            modifier = Modifier.size(Dimensions.PlayerControlSize),
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.25f)
                            )
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_add_to_playlist),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }

                        IconButton(
                            onClick = { viewModel.toggleFavorite(!track.favorite) },
                            modifier = Modifier.size(Dimensions.PlayerControlSize),
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.25f)
                            )
                        ) {
                            Icon(
                                painter = painterResource(
                                    if (track.favorite) R.drawable.ic_add_to_favourites_filled
                                    else R.drawable.ic_add_to_favourites_outline
                                ),
                                contentDescription = null,
                                tint = if (track.favorite) Color.Red
                                else MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
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
                                        viewModel.insertTrackToPlaylist(playlist.id)
                                        showBottomSheet = false
                                    }
                                }
                            }
                        }
                    }
                }
            } ?: run {
                Text(
                    text = stringResource(R.string.track_not_found_text),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        }
    }
}

private fun formatMs(ms: Int): String {
    val totalSeconds = ms / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%d:%02d".format(minutes, seconds)
}