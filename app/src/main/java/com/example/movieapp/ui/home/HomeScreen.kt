package com.example.movieapp.ui.home

import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.movieapp.di.DIContainer
import com.example.movieapp.di.Factory
import com.example.movieapp.doman.model.MovieResponse
import com.example.movieapp.doman.model.ResultMovie
import com.example.movieapp.presentation.state.ResultStates
import com.example.movieapp.presentation.viewmodel.MovieViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: MovieViewModel = viewModel(factory = Factory(DIContainer.provideMovieRepository()))
) {
    val moviesState by viewModel.movies.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchPopularMovies()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Popular Movies") })
        },
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
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
                        val movies =
                            (moviesState as ResultStates.Success<MovieResponse>).data.results
                        MovieList(movies = movies)
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
fun MovieList(movies: List<ResultMovie>) {
    LazyColumn {
        items(movies) { movie ->
            MovieItem(movie = movie)
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun MovieItem(movie: ResultMovie) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Image(
            painter = rememberImagePainter("https://image.tmdb.org/t/p/w500${movie.posterPath}"),
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = movie.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text("Release Date: ${movie.releaseDate}")
            Spacer(modifier = Modifier.height(4.dp))
            Text("Rating: ${movie.voteAverage}")
        }
    }
}