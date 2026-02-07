package com.example.playlist_maker_android.ui.playlists.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlist_maker_android.R
import com.example.playlist_maker_android.data.Playlist
import com.example.playlist_maker_android.ui.theme.Dimensions

@Composable
fun PlaylistListItem(
    playlist: Playlist,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimensions.PlaylistsItemHeight)
            .clickable(onClick = { onClick() }),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.width(13.dp))

        Image(
            painter = painterResource(id = R.drawable.ic_playlist_default_image),
            contentDescription = playlist.name,
            modifier = Modifier.size(Dimensions.PlaylistImageSmallSize),
//            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant)
        )

        Spacer(Modifier.width(8.dp))

        Column(
//            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = playlist.name,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onPrimary
                )

            )

            val text = "${playlist.tracks.size} tracks"
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}