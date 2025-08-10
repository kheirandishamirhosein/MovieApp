package com.example.movieapp.presentation.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movieapp.data.remote.model.movie.ResultMovie
import com.example.movieapp.presentation.movie.list.MovieItem

@Composable
fun StaticMovieList(
    movies: List<ResultMovie>,
    onItemClick: (ResultMovie) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(movies.size) { index ->
            val movie = movies[index]
            MovieItem(movie = movie, onClick = { onItemClick(movie) })
        }
    }
}