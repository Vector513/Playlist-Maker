package com.example.playlist_maker_android.buttons

import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.playlist_maker_android.BaseButton
import com.example.playlist_maker_android.CommonButtonContent
import com.example.playlist_maker_android.R
import com.example.playlist_maker_android.SettingsActivity
import com.example.playlist_maker_android.ui.theme.Dimensions
import com.example.playlist_maker_android.ui.theme.PlaylistmakerandroidTheme

@Composable
internal fun SettingsButtonContent() {
    CommonButtonContent(
        painterResource(R.drawable.ic_settings_light_mode),
        stringResource(R.string.settings_button_text)
    )
}

@Composable
internal fun SettingsButton(
    onNavigateToSettings: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val defaultNavigate = {
        val intent = Intent(context, SettingsActivity::class.java)
        context.startActivity(intent)
    }
    
    BaseButton(
        onClick = onNavigateToSettings ?: defaultNavigate,
        modifier = Modifier.padding(horizontal = Dimensions.ButtonHorizontalPadding),
        content = { SettingsButtonContent() }
    )
}

@Preview(showSystemUi = true, showBackground = false)
@Composable
internal fun SettingsButtonPreview() {
    PlaylistmakerandroidTheme {
        SettingsButton(onNavigateToSettings = {})
    }
}