package com.example.playlist_maker_android.ui.player

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.playlist_maker_android.R
import com.example.playlist_maker_android.domain.PlayerState
import com.example.playlist_maker_android.ui.theme.Dimensions

@Composable
fun MiniPlayer(
    playerState: PlayerState,
    onPlayPauseClick: () -> Unit,
    onMiniPlayerClick: () -> Unit,
    onSeek: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = playerState.currentTrack != null,
        enter = slideInVertically { it },
        exit = slideOutVertically { it }
    ) {
        playerState.currentTrack?.let { track ->
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                var isDragging by remember { mutableStateOf(false) }
                var dragProgress by remember { mutableFloatStateOf(0f) }

                val displayProgress = if (isDragging) {
                    dragProgress
                } else if (playerState.durationMs > 0) {
                    playerState.currentPositionMs.toFloat() / playerState.durationMs
                } else 0f

                val currentProgress by rememberUpdatedState(displayProgress)

                val activeColor = MaterialTheme.colorScheme.onPrimary
                val inactiveColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f)
                val dotColor = MaterialTheme.colorScheme.onPrimary

                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(SEEK_TOUCH_HEIGHT)
                        .pointerInput(playerState.durationMs) {
                            if (playerState.durationMs <= 0) return@pointerInput
                            detectTapGestures { offset ->
                                val tapProgress = (offset.x / size.width).coerceIn(0f, 1f)
                                onSeek((tapProgress * playerState.durationMs).toInt())
                            }
                        }
                        .pointerInput(playerState.durationMs) {
                            if (playerState.durationMs <= 0) return@pointerInput
                            detectHorizontalDragGestures(
                                onDragStart = {
                                    dragProgress = currentProgress
                                    isDragging = true
                                },
                                onDragEnd = {
                                    onSeek((dragProgress * playerState.durationMs).toInt())
                                    isDragging = false
                                },
                                onDragCancel = {
                                    isDragging = false
                                },
                                onHorizontalDrag = { _, dragAmount ->
                                    val delta = dragAmount / size.width
                                    dragProgress = (dragProgress + delta).coerceIn(0f, 1f)
                                }
                            )
                        }
                ) {
                    val trackHeight = TRACK_HEIGHT.toPx()
                    val y = size.height / 2
                    val dotRadius = DOT_RADIUS.toPx()
                    val progressX = size.width * displayProgress

                    drawLine(
                        color = activeColor,
                        start = Offset(0f, y),
                        end = Offset(progressX, y),
                        strokeWidth = trackHeight
                    )
                    drawLine(
                        color = inactiveColor,
                        start = Offset(progressX, y),
                        end = Offset(size.width, y),
                        strokeWidth = trackHeight
                    )
                    if (displayProgress > 0f) {
                        drawCircle(
                            color = dotColor,
                            radius = dotRadius,
                            center = Offset(progressX, y)
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimensions.MiniPlayerHeight)
                        .clickable { onMiniPlayerClick() }
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = track.image,
                        contentDescription = null,
                        modifier = Modifier
                            .size(Dimensions.MiniPlayerImageSize)
                            .clip(RoundedCornerShape(4.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(Modifier.width(12.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = track.trackName,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = track.artistName,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.tertiary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    IconButton(onClick = onPlayPauseClick) {
                        Icon(
                            painter = painterResource(
                                if (playerState.isPlaying) R.drawable.ic_pause
                                else R.drawable.ic_play
                            ),
                            contentDescription = stringResource(
                                if (playerState.isPlaying) R.string.pause_description
                                else R.string.play_description
                            ),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
}

private val TRACK_HEIGHT = 3.dp
private val DOT_RADIUS = 5.dp
private val SEEK_TOUCH_HEIGHT = 10.dp
