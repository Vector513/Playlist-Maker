package com.example.playlist_maker_android.ui.playlists

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.playlist_maker_android.R
import com.example.playlist_maker_android.domain.Track
import com.example.playlist_maker_android.ui.search.components.ArrowBackButton
import com.example.playlist_maker_android.ui.search.components.TrackListItem
import com.example.playlist_maker_android.ui.theme.Dimensions
import com.example.playlist_maker_android.ui.viewmodel.PlaylistViewModel

@Composable
fun PlaylistScreen(
    modifier: Modifier = Modifier,
    playlistViewModel: PlaylistViewModel,
    index: Int,
    onClick: (Long) -> Unit,
    onBack: () -> Unit
) {
    val playlist by playlistViewModel.playlist.collectAsState(null)

    Scaffold { innerPadding ->
        Column(
            modifier = modifier
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
                    ArrowBackButton { onBack() }
                }
            }

            if (playlist == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Загрузка...",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            }
            else {
                val tracks = playlist?.tracks.orEmpty()
                val totalSeconds = tracks.sumOf { track ->
                    parseTrackTimeSeconds(track.trackTime)
                }
                val totalMinutes = totalSeconds / 60
                val durationText = formatMinutesRu(totalMinutes)
                val tracksCountText = formatTracksCountRu(tracks.size)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(
                            id = R.drawable.ic_playlist_default_image
                        ),
                        contentDescription = null,
                        modifier = Modifier.size(79.dp),
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = playlist?.name ?: "",
                    modifier = Modifier.padding(start = 16.dp),
                    style = MaterialTheme.typography.displayLarge.copy(
                        color = MaterialTheme.colorScheme.onPrimary,
                        textAlign = TextAlign.Center
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = playlist?.description ?: "",
                    modifier = Modifier.padding(start = 16.dp),
                    style = MaterialTheme.typography.displayMedium.copy(
                        color = MaterialTheme.colorScheme.onPrimary,
                        textAlign = TextAlign.Center
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .height(21.dp)
                        .fillMaxWidth()
                        .padding(start = 16.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = durationText,
                        style = MaterialTheme.typography.displayMedium.copy(
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(13.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_point),
                            contentDescription = "point",
                            modifier = Modifier.size(Dimensions.PointSize),
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant)
                        )
                    }

                    Text(
                        text = tracksCountText,
                        style = MaterialTheme.typography.displayMedium.copy(
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(24.dp)
                        .clickable {},
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(
                            id = R.drawable.ic_playlist_more
                        ),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

//                if (playlist!!.tracks.isEmpty()) {
//                    Column(
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        modifier = Modifier.fillMaxSize()
//                    ) {
//                        Spacer(modifier = Modifier.height(150.dp))
//                        Image(
//                            painter = painterResource(id = R.drawable.ic_music_not_found),
//                            contentDescription = "Music not found",
//                            modifier = Modifier
//                                .width(Dimensions.MusicNotFoundSize)
//                                .height(Dimensions.MusicNotFoundSize),
//                            alignment = Alignment.Center
//                        )
//                        Spacer(modifier = Modifier.height(16.dp))
//                        Text(
//                            text = stringResource(R.string.playlist_empty_text),
//                            style = MaterialTheme.typography.bodyLarge.copy(
//                                color = MaterialTheme.colorScheme.onPrimary
//                            )
//                        )
//                    }
//                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(playlist!!.tracks) { track ->
                            TrackListItem(
                                track = track,
                                onClick = { onClick(track.id) }
                            )
                        }
                    }
//                }
            }
        }
    }
}

private fun parseTrackTimeSeconds(trackTime: String): Int {
    // Ожидаемый формат: "m:ss" или "mm:ss". На всякий случай обрабатываем ошибки.
    val parts = trackTime.trim().split(":")
    if (parts.size != 2) return 0
    val minutes = parts[0].toIntOrNull() ?: return 0
    val seconds = parts[1].toIntOrNull() ?: return 0
    return (minutes * 60 + seconds).coerceAtLeast(0)
}

private fun formatMinutesRu(minutes: Int): String {
    val m = minutes.coerceAtLeast(0)
    val word = pluralRu(m, one = "минута", few = "минуты", many = "минут")
    return "$m $word"
}

private fun formatTracksCountRu(count: Int): String {
    val c = count.coerceAtLeast(0)
    val word = pluralRu(c, one = "трек", few = "трека", many = "треков")
    return "$c $word"
}

private fun pluralRu(number: Int, one: String, few: String, many: String): String {
    val n = number % 100
    if (n in 11..14) return many
    return when (number % 10) {
        1 -> one
        2, 3, 4 -> few
        else -> many
    }
}
