package com.example.playlist_maker_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.playlist_maker_android.buttons.FavouritesButton
import com.example.playlist_maker_android.buttons.PlaylistButton
import com.example.playlist_maker_android.buttons.SearchButton
import com.example.playlist_maker_android.buttons.SettingsButton
import com.example.playlist_maker_android.ui.theme.PlaylistmakerandroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlaylistmakerandroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    Menu()
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Menu() {
    Surface(
        shape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        ),
//        color = Color.Gray,
        modifier = Modifier
            
            .padding(start = 0.dp, top = 14.dp, end = 0.dp, bottom = 0.dp)
    ) {
        Column() {
            SearchButton()
            PlaylistButton()
            FavouritesButton()
            SettingsButton()
        }
    }
}

val YSDisplay = FontFamily(
    Font(R.font.ys_display_medium, FontWeight.Medium)
)
