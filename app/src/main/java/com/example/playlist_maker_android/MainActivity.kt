package com.example.playlist_maker_android

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.playlist_maker_android.buttons.FavouritesButton
import com.example.playlist_maker_android.buttons.PlaylistButton
import com.example.playlist_maker_android.buttons.SearchButton
import com.example.playlist_maker_android.buttons.SettingsButton
import com.example.playlist_maker_android.ui.theme.Dimensions
import com.example.playlist_maker_android.ui.theme.PlaylistmakerandroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            PlaylistmakerandroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    PlaylistHost(navController)
                }
            }
        }
    }
}


@Composable
internal fun MainScreen(
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
            ) {
            PanelHeader()
            Menu(
                onSearchClick = onSearchClick,
                onSettingsClick = onSettingsClick
            )
        }
    }
}

@Composable
private fun Menu(
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val context = LocalContext.current
    
    Surface(
        shape = RoundedCornerShape(
            topStart = Dimensions.MenuCornerRadius,
            topEnd = Dimensions.MenuCornerRadius,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        ),
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier
            .fillMaxHeight()
            .padding(
                start = 0.dp,
                top = Dimensions.MenuTopPadding,
                end = 0.dp,
                bottom = 0.dp
            )
    ) {
        Column {
            // TODO: Заменить Spacer на modifier.safePadding...
            //  Создать AppSpacing для padding

            Spacer(modifier = Modifier
                .height(Dimensions.ButtonVerticalPadding))
            SearchButton(onNavigateToSearch = onSearchClick)
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
            SettingsButton(onNavigateToSettings = onSettingsClick)
        }
    }
}
