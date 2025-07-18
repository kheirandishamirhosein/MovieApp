package com.example.movieapp.presentation.movie.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.movieapp.data.remote.model.movie.MovieCreditsResponse
import com.example.movieapp.data.remote.model.movie.ResultMovie
import com.example.movieapp.presentation.movie.list.MovieList
import com.example.movieapp.presentation.movie.viewmodel.MovieUiEvent
import com.example.movieapp.presentation.movie.viewmodel.MovieViewModel
import com.example.movieapp.presentation.state.ResultStates
import com.example.movieapp.util.voteAverageFormatted

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoilApi::class)
@Composable
fun DetailsMoviesScreen(
    navController: NavController,
    movieId: Int,
    viewModel: MovieViewModel = hiltViewModel()
) {
    val movieDetailState by viewModel.movieDetailState.collectAsState()
    val castState by viewModel.castState.collectAsState()
    val similarMovies = viewModel.similarMovies(movieId).collectAsLazyPagingItems()

    LaunchedEffect(movieId) {
        viewModel.onEvent(MovieUiEvent.LoadMovieDetails(movieId))
        viewModel.onEvent(MovieUiEvent.LoadMovieCredits(movieId))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (movieDetailState) {
                is ResultStates.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is ResultStates.Success -> {
                    val movie = (movieDetailState as ResultStates.Success<ResultMovie>).data
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp)
                    ) {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        ) {
                            Image(
                                painter = rememberImagePainter("https://image.tmdb.org/t/p/w500${movie.backdropPath}"),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = movie.originalTitle,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Release Date: ${movie.releaseDate}",
                                fontSize = 14.sp,
                                color = Color.Magenta
                            )
                            Text(
                                text = "Popularity: ${movie.popularity}",
                                fontSize = 14.sp,
                                color = Color.Magenta
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = movie.overview,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = "Vote",
                                tint = Color.Yellow
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Rating: ${movie.voteAverage.voteAverageFormatted()} (${movie.voteCount} votes)")
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        when (castState) {
                            is ResultStates.Success -> {
                                val credits =
                                    (castState as ResultStates.Success<MovieCreditsResponse>).data

                                Text(
                                    text = "Cast",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(start = 8.dp)
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                CastList(castList = credits.cast ?: emptyList())
                            }

                            else -> {}
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        when (castState) {
                            is ResultStates.Success -> {
                                Text(
                                    text = "Similar Movies",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                MovieList(
                                    movies = similarMovies, onItemClick = {
                                        navController.navigate("movieDetail/${it.id}")
                                    }
                                )
                            }
                            else -> {}
                        }
                    }
                }

                is ResultStates.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Text("Error: ${(movieDetailState as ResultStates.Error).exception.message}")
                    }
                }
            }
        }
    }

}