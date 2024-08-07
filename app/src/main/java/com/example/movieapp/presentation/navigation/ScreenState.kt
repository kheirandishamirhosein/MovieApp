package com.example.movieapp.presentation.navigation

import androidx.compose.ui.graphics.vector.ImageVector

sealed class ScreenState(val route: String, val icon: ImageVector, val title: String) {
    class PopularMovies(icon: ImageVector) : ScreenState("popular_movies", icon, "Movies")
    class UpcomingMovies(icon: ImageVector) : ScreenState("upcoming_movies", icon, "Upcoming Movies")
    class PopularTVShows(icon: ImageVector) : ScreenState("popular_tv_shows", icon, "TV Shows")
}