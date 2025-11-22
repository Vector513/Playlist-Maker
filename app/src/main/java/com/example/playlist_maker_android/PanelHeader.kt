package com.example.playlist_maker_android

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.playlist_maker_android.ui.theme.AppColors
import com.example.playlist_maker_android.ui.theme.AppTypography
import com.example.playlist_maker_android.ui.theme.Dimensions
import com.example.playlist_maker_android.ui.theme.PlaylistmakerandroidTheme

@Composable
internal fun PanelHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimensions.PanelHeaderHeight)
            .background(AppColors.PrimaryBlue)
    ) {
        Text(
            text = stringResource(R.string.panel_header_text),
            color = AppColors.White,
            style = AppTypography.PanelHeaderText,
            modifier = Modifier
                .padding(
                    start = Dimensions.PanelHeaderPadding,
                    top = Dimensions.PanelHeaderTopPadding
                )
        )
    }
}

@Preview(device = "spec:width=411dp,height=891dp", showSystemUi = true, showBackground = true)
@Composable
internal fun PanelHeaderPreview() {
    PlaylistmakerandroidTheme {
        PanelHeader()
    }
}