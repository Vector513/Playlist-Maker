package com.example.playlist_maker_android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.playlist_maker_android.ui.main.MainScreen
import com.example.playlist_maker_android.ui.favourites.FavouritesScreen
import com.example.playlist_maker_android.ui.playlists.PlaylistsScreen
import com.example.playlist_maker_android.ui.playlists.NewPlaylistScreen
import com.example.playlist_maker_android.ui.playlists.PlaylistScreen
import com.example.playlist_maker_android.ui.search.SearchScreen
import com.example.playlist_maker_android.ui.settings.SettingsScreen
import com.example.playlist_maker_android.ui.track.TrackScreen
import com.example.playlist_maker_android.ui.viewmodel.PlaylistsViewModel
import com.example.playlist_maker_android.ui.viewmodel.PlaylistViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.playlist_maker_android.domain.Track

enum class Screen(val route: String) {
    MAIN("main"),
    SEARCH("search"),
    PLAYLISTS("playlists"),
    PLAYLIST("playlist"),
    NEWPLAYLIST("newplaylist"),
    TRACK("track"),
    SETTINGS("settings"),
    FAVOURITES("favourites")
}

@Composable
fun PlaylistHost(navController: NavHostController) {
    val playlistsViewModel: PlaylistsViewModel = viewModel(
        factory = PlaylistsViewModel.getViewModelFactory()
    )

    NavHost(
        navController = navController,
        startDestination = Screen.MAIN.route
    ) {
        composable(Screen.MAIN.route) {
            MainScreen(
                onSearchClick = {
                    navigateToSearch(navController)
                },
                onPlaylistsClick = {
                    navigateToPlaylists(navController)
                },
                onFavouritesClick = {
                    navigateToFavourites(navController)
                },
                onSettingsClick = {
                    navigateToSettings(navController)
                }
            )
        }

        composable(Screen.SEARCH.route) {
            SearchScreen(
                onTrackClick = { track ->
                    navigateToTrack(navController, track.id)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.PLAYLISTS.route) {
            PlaylistsScreen(
                playlistsViewModel = playlistsViewModel,
                addNewPlaylist = { navigateToNewPlayList(navController) },
                navigateBack = { navController.popBackStack() },
                navigateToPlaylist = { index ->
                    navigateToPlayList(navController, index)
                }
            )
        }
        
        composable(
            route = "${Screen.PLAYLIST.route}/{index}",
            arguments = listOf(
                navArgument("index") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val index = backStackEntry.arguments?.getInt("index") ?: 0
            val playlists by playlistsViewModel.playlists.collectAsState(emptyList())
            
            // Получаем playlistId из индекса
            val playlistId = if (index < playlists.size) {
                playlists[index].id
            } else {
                // Fallback: если индекс не найден, используем index + 1 как id
                (index + 1).toLong()
            }
            
            val playlistViewModel: PlaylistViewModel = viewModel(
                factory = PlaylistViewModel.getViewModelFactory(playlistId)
            )
            
            PlaylistScreen(
                modifier = Modifier,
                playlistViewModel = playlistViewModel,
                index = index,
                onClick = { trackIndex -> navigateToTrack(navController, trackIndex.toLong()) },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.NEWPLAYLIST.route) {
            NewPlaylistScreen(
                playlistsViewModel = playlistsViewModel,
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.FAVOURITES.route) {
            FavouritesScreen(
                playlistsViewModel = playlistsViewModel,
                onBack = {
                    navController.popBackStack()
                },
                onTrackClick = { track ->
                    navigateToTrack(navController, track.id)
                }
            )
        }

        composable("${Screen.TRACK.route}/{trackId}") { backStackEntry ->
            val trackId = backStackEntry.arguments?.getString("trackId")?.toLongOrNull()

            if (trackId != null) {
                TrackScreen(
                    trackId = trackId,
                    playlistsViewModel = playlistsViewModel,
                    onBack = {
                        val popped = navController.popBackStack()
                        if (!popped) {
                            navigateToMain(navController)
                        }
                    }
                )
            } else {
                navController.popBackStack()
            }
        }

        composable(Screen.SETTINGS.route) {
            SettingsScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}


private fun navigateToSearch(navController: NavController) {
    navController.navigate(Screen.SEARCH.route)
}

private fun navigateToPlaylists(navController: NavController) {
    navController.navigate(Screen.PLAYLISTS.route)
}

private fun navigateToPlayList(navController: NavController, index: Int) {
    navController.navigate("${Screen.PLAYLIST.route}/$index")
}

private fun navigateToNewPlayList(navController: NavController) {
    navController.navigate(Screen.NEWPLAYLIST.route)
}

private fun navigateToTrack(navController: NavController, trackId: Long) {
    navController.navigate("${Screen.TRACK.route}/$trackId")
}

private fun navigateToSettings(navController: NavController) {
    navController.navigate(Screen.SETTINGS.route)
}

private fun navigateToMain(navController: NavController) {
    navController.navigate(Screen.MAIN.route) {
        popUpTo(Screen.MAIN.route) { inclusive = true }
    }
}

private fun navigateToFavourites(navController: NavController) {
    navController.navigate(Screen.FAVOURITES.route)
}
