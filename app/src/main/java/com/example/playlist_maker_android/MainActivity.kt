package com.example.playlist_maker_android

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.playlist_maker_android.buttons.FavouritesButton
import com.example.playlist_maker_android.buttons.PlaylistButton
import com.example.playlist_maker_android.buttons.SearchButton
import com.example.playlist_maker_android.buttons.SettingsButton
import com.example.playlist_maker_android.ui.theme.AppColors
import com.example.playlist_maker_android.ui.theme.Dimensions
import com.example.playlist_maker_android.ui.theme.PlaylistmakerandroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlaylistmakerandroidTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .background(AppColors.PrimaryBlue)
                            .padding(innerPadding)
                    ) {
                        PanelHeader()
                        Menu()
                    }
                }
            }
        }
    }
}

@Preview(device = "spec:width=411dp,height=891dp", showSystemUi = true, showBackground = true)
@Composable
private fun Main() {
    PlaylistmakerandroidTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(AppColors.PrimaryBlue)
            ) {
                PanelHeader()
                Menu()
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Menu() {
    val context = LocalContext.current
    
    Surface(
        shape = RoundedCornerShape(
            topStart = Dimensions.MenuCornerRadius,
            topEnd = Dimensions.MenuCornerRadius,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        ),
        color = AppColors.White,
        modifier = Modifier
            .background(AppColors.Transparent)
            .fillMaxHeight()
            .padding(
                start = 0.dp,
                top = Dimensions.MenuTopPadding,
                end = 0.dp,
                bottom = 0.dp
            )
    ) {
        Column {
            SearchButton()
            PlaylistButton(onClick = {
                Toast.makeText(
                    context,
                    "Нажата кнопка Плейлисты",
                    Toast.LENGTH_SHORT
                ).show()
            })
            FavouritesButton(onClick = {
                Toast.makeText(
                    context,
                    "Нажата кнопка Избранное",
                    Toast.LENGTH_SHORT
                ).show()
            })
            SettingsButton()
        }
    }
}
