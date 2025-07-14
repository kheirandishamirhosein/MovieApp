package com.example.movieapp.presentation.movie.list

import androidx.compose.foundation.layout.*
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
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movieapp.presentation.movie.viewmodel.MovieUiEvent
import com.example.movieapp.presentation.movie.viewmodel.MovieViewModel
import com.example.movieapp.presentation.state.ResultStates

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
    navController: NavController,
    viewModel: MovieViewModel = hiltViewModel()
) {
    val moviesState by viewModel.popularMoviesState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(MovieUiEvent.LoadPopularMovies)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Movies") })
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {

                when (moviesState) {
                    is ResultStates.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is ResultStates.Success -> {
                        val movies = (moviesState as ResultStates.Success).data
                        val topMovies = movies.sortedByDescending { it.voteAverage }.take(6)

                        MovieCarousel(movies = topMovies)
                        MovieList(movies = movies) { movie ->
                            navController.navigate("movieDetail/${movie.id}")
                        }
                    }

                    is ResultStates.Error -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Error: ${(moviesState as ResultStates.Error).exception.message}")
                        }
                    }
                }

            }
        }
    )
}