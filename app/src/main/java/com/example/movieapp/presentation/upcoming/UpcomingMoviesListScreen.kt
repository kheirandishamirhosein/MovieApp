package com.example.movieapp.presentation.upcoming

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movieapp.data.remote.model.movie.ResultMovie
import com.example.movieapp.presentation.movie.list.MovieItem
import com.example.movieapp.presentation.state.ResultStates
import com.example.movieapp.presentation.upcoming.viewmodel.UpcomingMovieViewModel
import com.example.movieapp.presentation.upcoming.viewmodel.UpcomingUiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpcomingMoviesScreen(viewModel: UpcomingMovieViewModel = hiltViewModel()) {
    val upcomingMovies by viewModel.upcomingMoviesState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(UpcomingUiEvent.LoadUpcomingMovies)
    }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Upcoming Movies") })
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
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
                        val movies =
                            (upcomingMovies as ResultStates.Success<List<ResultMovie>>).data
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
        }
    )
}