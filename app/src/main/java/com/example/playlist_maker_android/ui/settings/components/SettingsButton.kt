package com.example.playlist_maker_android.ui.settings.components

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
internal fun SettingsButtonContent() {
    _root_ide_package_.com.example.playlist_maker_android.ui.components.buttons.CommonButtonContent(
        painterResource(R.drawable.ic_settings),
        stringResource(R.string.settings_button_text)
    )
}

@Composable
internal fun SettingsButton(
    onNavigateToSettings: (() -> Unit)
) {
    _root_ide_package_.com.example.playlist_maker_android.ui.components.buttons.BaseButton(
        onClick = onNavigateToSettings,
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