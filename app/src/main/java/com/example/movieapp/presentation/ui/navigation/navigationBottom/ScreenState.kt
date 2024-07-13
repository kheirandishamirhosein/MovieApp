package com.example.movieapp.presentation.ui.navigation.navigationBottom

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ScreenState(val route: String, val icon: ImageVector, val title: String) {
    data object PopularMovies :
        ScreenState("popular_movies", Icons.Default.Favorite, "Popular Movies")

    data object UpcomingMovies :
        ScreenState("upcoming_movies", Icons.Default.DateRange, "upcoming_movies")

    data object PopularTVShows :
        ScreenState("popular_tv_shows", Icons.Default.AccountBox, "Popular TV Shows")
}