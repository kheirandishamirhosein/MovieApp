package com.example.movieapp.presentation.movie.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.movieapp.data.remote.model.movie.ResultMovie
import com.example.movieapp.presentation.movie.viewmodel.MovieUiEvent
import com.example.movieapp.presentation.movie.viewmodel.MovieViewModel
import com.example.movieapp.presentation.state.ResultStates
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopularMovieListScreen(
    navController: NavController,
    viewModel: MovieViewModel = hiltViewModel()
) {
    val moviesState by viewModel.popularMoviesState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(MovieUiEvent.LoadPopularMovies)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Popular Movies") })
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

@Composable
fun MovieList(movies: List<ResultMovie>, onItemClick: (ResultMovie) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(movies) { movie ->
                MovieItem(movie = movie, onClick = { onItemClick(movie) })
            }
        }
    }

}

@Composable
fun MovieCarousel(movies: List<ResultMovie>) {
    var currentIndex by remember { mutableIntStateOf(0) }
    LaunchedEffect(currentIndex) {
        delay(5000)
        currentIndex = (currentIndex + 1) % movies.size
    }
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        movies.forEachIndexed { index, movie ->
            AnimatedVisibility(
                visible = index == currentIndex,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                MovieCard(movie = movie)
            }
        }
    }
}
