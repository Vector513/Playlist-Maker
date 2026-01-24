package com.example.playlist_maker_android.ui.favourites.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.playlist_maker_android.R
import com.example.playlist_maker_android.ui.theme.Dimensions
import com.example.playlist_maker_android.ui.theme.PlaylistmakerandroidTheme

@Composable
internal fun FavouritesButtonContent() {
    _root_ide_package_.com.example.playlist_maker_android.ui.components.buttons.CommonButtonContent(
        painterResource(R.drawable.ic_favourites),
        stringResource(R.string.favourites_button_text)
    )
}

@Composable
internal fun FavouritesButton(
    onClick: () -> Unit
) {
    _root_ide_package_.com.example.playlist_maker_android.ui.components.buttons.BaseButton(
        onClick = onClick,
        modifier = Modifier.padding(horizontal = Dimensions.ButtonHorizontalPadding),
        content = { FavouritesButtonContent() }
    )
}

@Preview(showSystemUi = true, showBackground = false)
@Composable
internal fun FavouritesButtonPreview() {
    PlaylistmakerandroidTheme {
        FavouritesButton(onClick = {})
    }
}