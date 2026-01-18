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
internal fun FavouritesButtonContent() {
    CommonButtonContent(
        painterResource(R.drawable.ic_favourites),
        stringResource(R.string.favourites_button_text)
    )
}

@Composable
internal fun FavouritesButton(
    onClick: () -> Unit
) {
    BaseButton(
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