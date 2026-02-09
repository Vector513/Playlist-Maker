package com.example.playlist_maker_android.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.playlist_maker_android.R
import com.example.playlist_maker_android.domain.Track
import com.example.playlist_maker_android.ui.search.components.TrackListItem
import com.example.playlist_maker_android.ui.theme.Dimensions

@Composable
fun SearchEmptyState(
    text: CharSequence
) {
    if (text.isEmpty())  {
        Text(
            "Введите строку для поиска",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center
        )
    }
    else {
        SearchLoadingState()
    }
}

@Composable
fun SearchLoadingState() {
    CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
}

@Composable
fun SearchErrorState(error: String) {
    Text(
        text = "Ошибка: $error",
        style = MaterialTheme.typography.bodyLarge.copy(
            color = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
fun SearchServerErrorState(onRetry: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(102.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_music_not_found),
            contentDescription = "Server error",
            modifier = Modifier
                .width(Dimensions.MusicNotFoundSize)
                .height(Dimensions.MusicNotFoundSize),
            alignment = Alignment.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.server_error_text),
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onPrimary
            ),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onRetry,
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.25f)
            )
        ) {
            Text(
                text = stringResource(R.string.retry_button_text),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }
}

@Composable
fun SearchResultsState(
    tracks: List<Track>,
    onClick: (Track) -> Unit
) {
    if (tracks.isEmpty()) {
        SearchNoResults()
    } else {
        Spacer(Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(tracks.size) { index ->
                TrackListItem(
                    track = tracks[index],
                    onClick = { onClick(tracks[index]) }
                )
            }
        }
    }
}

@Composable
private fun SearchNoResults() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(102.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_music_not_found),
            contentDescription = "Music not found",
            modifier = Modifier
                .width(Dimensions.MusicNotFoundSize)
                .height(Dimensions.MusicNotFoundSize),
            alignment = Alignment.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.music_not_found_text),
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onPrimary
            )
        )
    }
}
