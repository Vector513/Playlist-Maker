package com.example.playlist_maker_android.buttons

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.playlist_maker_android.BaseButton
import com.example.playlist_maker_android.CommonButtonContent
import com.example.playlist_maker_android.R
import com.example.playlist_maker_android.ui.theme.Dimensions
import com.example.playlist_maker_android.ui.theme.PlaylistmakerandroidTheme

@Composable
internal fun PlaylistButton(
    onClick: () -> Unit
) {
    BaseButton(
        onClick = onClick,
        modifier = Modifier.padding(horizontal = Dimensions.ButtonHorizontalPadding),
        content = { PlaylistButtonContent() }
    )
}

@Preview(showSystemUi = true, showBackground = false, device = "spec:width=411dp,height=891dp")
@Composable
internal fun PlaylistButtonPreview() {
    PlaylistmakerandroidTheme {
        PlaylistButton(onClick = {})
    }
}

@Composable
internal fun PlaylistButtonContent() {
    CommonButtonContent(
        painterResource(R.drawable.ic_library_light_mode),
        stringResource(R.string.library_button_text)
    )
}
