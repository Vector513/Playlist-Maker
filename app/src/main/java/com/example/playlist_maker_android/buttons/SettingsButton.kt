package com.example.playlist_maker_android.buttons

import android.content.Intent
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
import com.example.playlist_maker_android.SettingsActivity

@Composable
internal fun SettingsButtonContent() {
    CommonButtonContent(
        painterResource(R.drawable.ic_settings_light_mode),
        stringResource(R.string.settings_button_text)
    )
}

@Composable
internal fun SettingsButton() {
    val context = LocalContext.current

    BaseButton(
        {
            val intent = Intent(context, SettingsActivity::class.java)
            context.startActivity(intent)
        },
        Modifier.padding(horizontal = 16.dp, vertical = 0.dp),
        { SettingsButtonContent() }
    )
}

@Preview(showSystemUi = true, showBackground = false)
@Composable
internal fun SettingsButtonPreview() {
    SettingsButton()
}