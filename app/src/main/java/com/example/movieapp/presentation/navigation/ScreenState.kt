package com.example.movieapp.presentation.navigation

import androidx.compose.ui.graphics.vector.ImageVector

sealed class ScreenState(val route: String, val icon: ImageVector, val title: String) {
    class PopularMovies(icon: ImageVector) : ScreenState("popular_movies", icon, "Movies")
    class Home(icon: ImageVector) : ScreenState("home_screen", icon, "Home")
    class PopularTVShows(icon: ImageVector) : ScreenState("popular_tv_shows", icon, "TV Shows")
}