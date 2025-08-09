package com.example.movieapp.presentation.movie.details

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoilApi::class)
@Composable
fun DetailsMoviesScreen(
    navController: NavController,
    movieId: Int,
    viewModel: MovieViewModel = hiltViewModel()
) {
    val movieDetailState by viewModel.movieDetailState.collectAsState()
    val castState by viewModel.castState.collectAsState()
    val similarMovies = viewModel.similarMovies.collectAsLazyPagingItems()
    val isLiked by viewModel.isLiked.collectAsState()

    LaunchedEffect(movieId) {
        viewModel.onEvent(MovieUiEvent.LoadMovieDetails(movieId))
        viewModel.onEvent(MovieUiEvent.LoadMovieCredits(movieId))
        viewModel.onEvent(MovieUiEvent.LoadSimilarMovies(movieId))
    }

    val isLoading = movieDetailState is ResultStates.Loading ||
            castState is ResultStates.Loading
            ||
            similarMovies.loadState.refresh is LoadState.Loading

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
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                item {
                    Log.d("isLiked", "$isLiked")
                    MovieDetailsSection(
                        movieDetailState = movieDetailState,
                        isLiked = isLiked,
                        onLikeClick = {viewModel.toggleLike(movieId = movieId)}
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    CastSection(castState)
                    Spacer(modifier = Modifier.height(24.dp))
                    SimilarMoviesSection(
                        similarMovies = similarMovies,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun MovieDetailsSection(
    movieDetailState: ResultStates<ResultMovie>,
    isLiked: Boolean,
    onLikeClick: () -> Unit
    ) {

    val movie = (movieDetailState as ResultStates.Success).data
    var showLikeMessage by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val onLikeClicked = {
        onLikeClick()
        showLikeMessage = true
        coroutineScope.launch {
            delay(3000)
            showLikeMessage = false
        }
        Unit
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = rememberImagePainter("https://image.tmdb.org/t/p/w500${movie.backdropPath}"),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    IconButton(
                        onClick = onLikeClicked,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = if (isLiked) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Like",
                            tint = if (isLiked) Color.Red else Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    AnimatedVisibility(
                        visible = showLikeMessage,
                        enter = slideInHorizontally(
                            initialOffsetX = { -40 },
                            animationSpec = tween(300)
                        ),
                        exit = slideOutHorizontally(
                            targetOffsetX = { 40 },
                            animationSpec = tween(300)
                        )
                    ) {
                        Text(
                            text = if (isLiked) "Liked" else "UnLiked",
                            color = Color.White,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(start = 4.dp, end = 8.dp)
                        )
                    }
                }
            }
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

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Default.Star,
                contentDescription = "Vote",
                tint = Color.Yellow
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text("Rating: ${movie.voteAverage.voteAverageFormatted()} (${movie.voteCount} votes)")
        }
    }
}

@Composable
fun CastSection(castState: ResultStates<MovieCreditsResponse>) {
    val cast = (castState as ResultStates.Success).data.cast ?: emptyList()

    Column {
        Text(
            text = "Cast",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        CastList(castList = cast)
    }
}

@Composable
fun SimilarMoviesSection(
    similarMovies: LazyPagingItems<ResultMovie>,
    navController: NavController
) {
    Column {
        Text(
            text = "Similar Movies",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        when(similarMovies.loadState.refresh) {
            is LoadState.Error -> {
                Text(
                    text = "Error loading similar movies: " +
                            "${(similarMovies.loadState.refresh as LoadState.Error).error.message}",
                    modifier = Modifier.padding(16.dp)
                )
            }
            else -> {
                MovieList(
                    movies = similarMovies,
                    onItemClick = { movie ->
                        navController.navigate("movieDetail/${movie.id}")
                    }
                )
            }
        }
    }
}