package com.example.movieapp.presentation.ui.navigation.navigationBottom

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.presentation.ui.navigation.popularMovie.movieList.compose.PopularMovieListScreen
import com.example.movieapp.presentation.ui.navigation.popularTv.PopularTvShowsListScreen
import com.example.movieapp.presentation.ui.navigation.uncoming.UpcomingMoviesScreen

@Composable
fun BottomNavigationScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues))
        NavHost(navController = navController, startDestination = ScreenState.PopularMovies.route) {
            composable(ScreenState.PopularMovies.route) { PopularMovieListScreen(navController = navController) }
            composable(ScreenState.UpcomingMovies.route) { UpcomingMoviesScreen() }
            composable(ScreenState.PopularTVShows.route) { PopularTvShowsListScreen() }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        ScreenState.PopularMovies,
        ScreenState.UpcomingMovies,
        ScreenState.PopularTVShows
    )
    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(imageVector = screen.icon, contentDescription = screen.title) },
                label = { Text(text = screen.title) },
                selected = navController.currentDestination?.route == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}