package com.example.playlist_maker_android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.playlist_maker_android.ui.main.MainScreen
import com.example.playlist_maker_android.ui.search.SearchScreen
import com.example.playlist_maker_android.ui.settings.SettingsScreen
import com.example.playlist_maker_android.ui.viewmodel.SearchViewModel

enum class Screen(val route: String) {
    MAIN("main"),
    SEARCH("search"),
    SETTINGS("settings")
}

@Composable
fun PlaylistHost(navController: NavHostController, searchViewModel: SearchViewModel) {
    NavHost(
        navController = navController,
        startDestination = Screen.MAIN.route
    ) {

        composable(Screen.MAIN.route) {
            MainScreen(
                onSearchClick = {
                    navigateToSearch(navController)
                },
                onSettingsClick = {
                    navigateToSettings(navController)
                }
            )
        }

        composable(Screen.SEARCH.route) {
            SearchScreen(
                searchViewModel,
                onBack = {
                    navController.popBackStack()
                }
            )
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

private fun navigateToSettings(navController: NavController) {
    navController.navigate(Screen.SETTINGS.route)
}

private fun navigateToMain(navController: NavController) {
    navController.navigate(Screen.MAIN.route) {
        popUpTo(Screen.MAIN.route) { inclusive = true }
    }
}
