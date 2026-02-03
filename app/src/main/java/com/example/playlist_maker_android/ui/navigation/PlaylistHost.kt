package com.example.playlist_maker_android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.playlist_maker_android.ui.main.MainScreen
import com.example.playlist_maker_android.ui.favourites.FavouritesScreen
import com.example.playlist_maker_android.ui.playlists.PlaylistsScreen
import com.example.playlist_maker_android.ui.search.SearchScreen
import com.example.playlist_maker_android.ui.settings.SettingsScreen
import com.example.playlist_maker_android.ui.track.TrackScreen
import com.example.playlist_maker_android.data.network.Track

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
                    navigateToTrack(navController, track)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.PLAYLISTS.route) {
            PlaylistsScreen(
                addNewPlaylist = { navigateToNewPlayList(navController) },
                navigateBack = { navController.popBackStack() }
            ) { index ->
                navigateToPlayList(navController, index)
            }
        }

        composable(Screen.FAVOURITES.route) {
            FavouritesScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("${Screen.TRACK.route}/{trackId}") { backStackEntry ->
            val trackId = backStackEntry.arguments?.getString("trackId")?.toLongOrNull()

            if (trackId != null) {
                TrackScreen(
                    trackId = trackId,
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

private fun navigateToTrack(navController: NavController, track: Track) {
    navController.navigate("${Screen.TRACK.route}/${track.id}")
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
