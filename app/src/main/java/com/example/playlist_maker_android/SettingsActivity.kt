package com.example.playlist_maker_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import com.example.playlist_maker_android.ui.theme.PlaylistmakerandroidTheme

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlaylistmakerandroidTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Settings Screen",
                        modifier = Modifier.padding(it)
                    )
                }
            }
        }
    }
}

