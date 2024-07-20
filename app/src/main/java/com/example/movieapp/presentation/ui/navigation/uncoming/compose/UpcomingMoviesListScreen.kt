package com.example.movieapp.presentation.ui.navigation.uncoming.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movieapp.data.remote.model.MovieResponse
import com.example.movieapp.presentation.state.ResultStates
import com.example.movieapp.presentation.ui.navigation.popularMovie.movieList.compose.MovieItem
import com.example.movieapp.presentation.ui.navigation.uncoming.viewmodel.UpcomingMovieViewModel

@Composable
fun UpcomingMoviesScreen(viewModel: UpcomingMovieViewModel = hiltViewModel()) {
    val upcomingMovies by viewModel.upcomingMovies.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchUpcomingMovies()
    }

    when (upcomingMovies) {
        is ResultStates.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is ResultStates.Success -> {
            val movies = (upcomingMovies as ResultStates.Success<MovieResponse>).data.results
            LazyColumn {
                items(movies) { movie ->
                    MovieItem(movie = movie, onClick = { /* Handle click */ })
                }
            }
        }

        is ResultStates.Error -> {
            Text("Error: ${(upcomingMovies as ResultStates.Error).exception.message}")
        }
    }

}