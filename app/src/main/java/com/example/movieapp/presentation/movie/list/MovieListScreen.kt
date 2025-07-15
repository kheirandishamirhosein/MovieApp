package com.example.movieapp.presentation.movie.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movieapp.data.remote.model.movie.ResultMovie
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
    val nowPlayingMovies = viewModel.nowPlayingMovies.collectAsLazyPagingItems()
    val topRatedMovies = viewModel.topRatedMovies.collectAsLazyPagingItems()
    val trendingMovies = viewModel.trendingMovies.collectAsLazyPagingItems()
    val similarMovies = viewModel.similarMovies

    LaunchedEffect(Unit) {
        viewModel.onEvent(MovieUiEvent.LoadPopularMovies)
        viewModel.onEvent(MovieUiEvent.LoadNowPlayingMovies)
        viewModel.onEvent(MovieUiEvent.LoadTopRatedMovies)
        viewModel.onEvent(MovieUiEvent.LoadTrendingMovies)
    }

    val isLoading =
        nowPlayingMovies.loadState.refresh is LoadState.Loading &&
                topRatedMovies.loadState.refresh is LoadState.Loading &&
                trendingMovies.loadState.refresh is LoadState.Loading &&
                moviesState is ResultStates.Loading

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Movies") })
        },
        content = { paddingValues ->
            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                item {
                    if (isLoading) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    } else {
                        when (moviesState) {
                            is ResultStates.Success -> {
                                val movies = (moviesState as ResultStates.Success).data
                                val topMovies = movies.sortedByDescending { it.voteAverage }.take(10)

                                MovieCarousel(movies = topMovies)
                            }

                            is ResultStates.Error -> {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("Error: ${(moviesState as ResultStates.Error).exception.message}")
                                }
                            }

                            else -> {}
                        }

                        MovieSection(
                            title = "Now Playing",
                            movies = nowPlayingMovies,
                            onItemClick = { movie ->
                                navController.navigate("movieDetail/${movie.id}")
                            }
                        )

                        MovieSection(
                            title = "Top Rated",
                            movies = topRatedMovies,
                            onItemClick = { movie ->
                                navController.navigate("movieDetail/${movie.id}")
                            }
                        )

                        MovieSection(
                            title = "Trending",
                            movies = trendingMovies,
                            onItemClick = { movie ->
                                navController.navigate("movieDetail/${movie.id}")
                            }
                        )

                    }
                }
            }
        }
    )
}

@Composable
fun MovieSection(
    title: String,
    movies: LazyPagingItems<ResultMovie>,
    onItemClick: (ResultMovie) -> Unit
) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
    )

    when (movies.loadState.refresh) {
        is LoadState.Error -> {
            Text(
                text = "Error loading " +
                        "$title: ${(movies.loadState.refresh as LoadState.Error).error.message}",
                modifier = Modifier.padding(16.dp)
            )
        }
        else -> {
            MovieList(movies = movies, onItemClick = onItemClick)
        }
    }
}