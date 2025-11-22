package com.example.playlist_maker_android.buttons

import android.content.Intent
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
import com.example.playlist_maker_android.SearchActivity
import com.example.playlist_maker_android.ui.theme.Dimensions
import com.example.playlist_maker_android.ui.theme.PlaylistmakerandroidTheme

@Composable
internal fun SearchButton(
    onNavigateToSearch: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val defaultNavigate = {
        val intent = Intent(context, SearchActivity::class.java)
        context.startActivity(intent)
    }
    
    BaseButton(
        onClick = onNavigateToSearch ?: defaultNavigate,
        modifier = Modifier.padding(
            start = Dimensions.ButtonHorizontalPadding,
            top = Dimensions.ButtonVerticalPadding,
            end = Dimensions.ButtonHorizontalPadding,
            bottom = 0.dp
        ),
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
    CommonButtonContent(
        painterResource(R.drawable.ic_search_light_mode),
        stringResource(R.string.search_button_text)
    )
}