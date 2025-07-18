package com.example.movieapp.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieapp.presentation.movie.details.DetailsMoviesScreen
import com.example.movieapp.presentation.movie.list.MovieListScreen
import com.example.movieapp.presentation.show.details.DetailsTVShowsScreen
import com.example.movieapp.presentation.show.lists.TvShowsListScreen
import com.example.movieapp.util.getIcons

@Composable
fun BottomNavigationScreen() {
    val navController = rememberNavController()
    val icons = getIcons()

    val items = listOf(
        ScreenState.PopularMovies(icons["movie"]!!),
        ScreenState.Home(Icons.Default.Home),
        ScreenState.PopularTVShows(icons["tv"]!!)
    )

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController, items = items) }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavHost(navController = navController, startDestination = items[0].route) {
                composable(items[0].route) { MovieListScreen(navController = navController) }
                composable(items[1].route) {  }
                composable(items[2].route) { TvShowsListScreen(navController = navController) }
                composable(
                    "movieDetail/{movieId}",
                    arguments = listOf(navArgument("movieId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
                    DetailsMoviesScreen(navController, movieId)
                }
                composable(
                    "tvShowsDetail/{tvShowId}",
                    arguments = listOf(navArgument("tvShowId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val tvShowId = backStackEntry.arguments?.getInt("tvShowId") ?: 0
                    DetailsTVShowsScreen(navController = navController, tvShowId = tvShowId)
                }
            }
        }
    }
}


@Composable
fun BottomNavigationBar(navController: NavHostController, items: List<ScreenState>) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(imageVector = screen.icon, contentDescription = screen.title) },
                label = { Text(text = screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}