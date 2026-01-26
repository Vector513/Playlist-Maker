package com.example.playlist_maker_android.ui.search.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.playlist_maker_android.R
import com.example.playlist_maker_android.ui.theme.Dimensions
import com.example.playlist_maker_android.ui.theme.PlaylistmakerandroidTheme

@Composable
fun SearchPanelHeader(onBack: () -> Unit) {
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
            Text(
                text = stringResource(R.string.search_panel_header_text),
                modifier = Modifier
                    .padding(start = 12.dp),
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Center
                ),
            )
        }
    }
}

@Preview(device = "spec:width=411dp,height=891dp", showSystemUi = true, showBackground = true)
@Composable
private fun SearchPanelHeaderPreview() {
    PlaylistmakerandroidTheme() {
        SearchPanelHeader {  }
    }
}