package com.example.playlist_maker_android.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.playlist_maker_android.ui.navigation.PlaylistHost
import com.example.playlist_maker_android.ui.navigation.Screen
import com.example.playlist_maker_android.ui.favourites.components.FavouritesButton
import com.example.playlist_maker_android.ui.player.MiniPlayer
import com.example.playlist_maker_android.ui.playlists.components.PlaylistButton
import com.example.playlist_maker_android.ui.search.components.SearchButton
import com.example.playlist_maker_android.ui.settings.components.SettingsButton
import com.example.playlist_maker_android.ui.main.components.PanelHeader
import com.example.playlist_maker_android.ui.theme.Dimensions
import com.example.playlist_maker_android.ui.theme.PlaylistmakerandroidTheme
import com.example.playlist_maker_android.ui.viewmodel.PlayerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val playerViewModel: PlayerViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestNotificationPermission()

        setContent {
            val navController = rememberNavController()
            val playerState by playerViewModel.playerState.collectAsState()

            PlaylistmakerandroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Column(modifier = Modifier.fillMaxSize().navigationBarsPadding()) {
                        Box(modifier = Modifier.weight(1f)) {
                            PlaylistHost(navController)
                        }
                        MiniPlayer(
                            playerState = playerState,
                            onPlayPauseClick = { playerViewModel.togglePlayPause() },
                            onMiniPlayerClick = {
                                playerState.currentTrack?.let { track ->
                                    navController.navigate("${Screen.TRACK.route}/${track.id}") {
                                        launchSingleTop = true
                                    }
                                }
                            },
                            onSeek = { playerViewModel.seekTo(it) }
                        )
                    }
                }
            }
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 0
                )
            }
        }
    }
}


@Composable
internal fun MainScreen(
    onSearchClick: () -> Unit,
    onPlaylistsClick: () -> Unit,
    onFavouritesClick: () -> Unit,
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
                onPlaylistsClick = onPlaylistsClick,
                onFavouritesClick = onFavouritesClick,
                onSettingsClick = onSettingsClick
            )
        }
    }
}

@Composable
private fun Menu(
    onSearchClick: () -> Unit,
    onPlaylistsClick: () -> Unit,
    onFavouritesClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
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
            Spacer(modifier = Modifier
                .height(Dimensions.ButtonVerticalPadding))
            SearchButton(onNavigateToSearch = onSearchClick)
            PlaylistButton(onClick = onPlaylistsClick)
            FavouritesButton(onClick = onFavouritesClick)
            SettingsButton(onNavigateToSettings = onSettingsClick)
        }
    }
}
