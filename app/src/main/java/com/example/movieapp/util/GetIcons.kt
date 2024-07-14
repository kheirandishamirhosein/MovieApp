package com.example.movieapp.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.movieapp.R

@Composable
fun getIcons(): Map<String, ImageVector> {
    return mapOf(
        "movie" to ImageVector.vectorResource(id = R.drawable.ic_movie),
        "tv" to ImageVector.vectorResource(id = R.drawable.ic_live_tv)
    )
}