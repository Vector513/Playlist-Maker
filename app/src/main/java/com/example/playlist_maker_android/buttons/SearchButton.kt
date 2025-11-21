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
internal fun SearchButton() {
    val context = LocalContext.current

    BaseButton(
    {
            Toast.makeText(
                context,
                "Нажата кнопка Поиск",
                Toast.LENGTH_SHORT
            ).show()
        },
        Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 0.dp),
        { SearchButtonContent() }
    )
}

@Preview(showSystemUi = true, showBackground = false, device = "spec:width=411dp,height=891dp")
@Composable
internal fun SearchButtonPreview() {
    SearchButton()
}

@Composable
private fun SearchButtonContent() {
    CommonButtonContent(
        painterResource(R.drawable.ic_search_light_mode),
        stringResource(R.string.search_button_text)
    )
}