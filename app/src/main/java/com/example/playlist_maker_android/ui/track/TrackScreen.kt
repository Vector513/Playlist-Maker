package com.example.playlist_maker_android.ui.track

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.playlist_maker_android.data.network.Track
import com.example.playlist_maker_android.data.network.listTracks
import com.example.playlist_maker_android.ui.search.components.ArrowBackButton
import com.example.playlist_maker_android.ui.theme.Dimensions

@Composable
fun TrackScreen(
    trackId: Long,
    onBack: () -> Unit
) {
    val track: Track? = listTracks.firstOrNull { it.id == trackId }

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

            if (track != null) {
                Text(
                    text = track.trackName,
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = track.artistName,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = track.trackTime,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                )
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