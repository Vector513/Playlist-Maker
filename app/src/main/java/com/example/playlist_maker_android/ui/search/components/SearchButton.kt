package com.example.playlist_maker_android.ui.search.components

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
internal fun SearchButton(
    onNavigateToSearch: (() -> Unit)
) {
    _root_ide_package_.com.example.playlist_maker_android.ui.components.buttons.BaseButton(
        onClick = onNavigateToSearch,
        modifier = Modifier
            .padding(horizontal = Dimensions.ButtonHorizontalPadding),
        content = { SearchButtonContent() }
    )
}

@Preview(showSystemUi = true, showBackground = false, device = "spec:width=411dp,height=891dp")
@Composable
internal fun SearchButtonPreview() {
    PlaylistmakerandroidTheme {
        SearchButton(onNavigateToSearch = {})
    }
}

@Composable
private fun SearchButtonContent() {
    _root_ide_package_.com.example.playlist_maker_android.ui.components.buttons.CommonButtonContent(
        painterResource(R.drawable.ic_search),
        stringResource(R.string.search_button_text)
    )
}