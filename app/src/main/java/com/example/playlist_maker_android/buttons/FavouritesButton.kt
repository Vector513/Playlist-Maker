package com.example.playlist_maker_android.buttons

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.playlist_maker_android.BaseButton
import com.example.playlist_maker_android.CommonButtonContent
import com.example.playlist_maker_android.R

@Composable
internal fun FavouritesButtonContent() {
    CommonButtonContent(
        painterResource(R.drawable.ic_favourites_light_mode),
        stringResource(R.string.favourites_button_text)
    )
}

@Composable
internal fun FavouritesButton() {
    val context = LocalContext.current

    BaseButton(
        {
            Toast.makeText(
                context,
                "Нажата кнопка Избранное",
                Toast.LENGTH_SHORT
            ).show()
        },
        Modifier.padding(horizontal = 16.dp, vertical = 0.dp),
        { FavouritesButtonContent() }
    )
}

@Preview(showSystemUi = true, showBackground = false)
@Composable
internal fun FavouritesButtonPreview() {
    FavouritesButton()
}