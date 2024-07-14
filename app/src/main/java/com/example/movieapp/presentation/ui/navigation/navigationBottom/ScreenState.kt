package com.example.movieapp.presentation.ui.navigation.navigationBottom

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.movieapp.R

sealed class ScreenState(val route: String, val icon: ImageVector, val title: String) {
    class PopularMovies(icon: ImageVector) : ScreenState("popular_movies", icon, "Movies")
    class UpcomingMovies(icon: ImageVector) : ScreenState("upcoming_movies", icon, "Upcoming Movies")
    class PopularTVShows(icon: ImageVector) : ScreenState("popular_tv_shows", icon, "TV Shows")
}