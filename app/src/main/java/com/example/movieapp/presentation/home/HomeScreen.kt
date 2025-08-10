package com.example.movieapp.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.movieapp.data.mapper.toResultMovie
import com.example.movieapp.data.mapper.toResultTVShow

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val likedMovies = viewModel.likedMovies.collectAsState().value
    val likedTVShows = viewModel.likedTVShows.collectAsState().value

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        if (likedMovies.isNotEmpty()) {
            item {
                Text(
                    text = "Liked Movies",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                )
            }

            item {
                StaticMovieList(
                    movies = likedMovies.map { it.toResultMovie() },
                    onItemClick = { movie ->
                        navController.navigate("movieDetails/${movie.id}")
                    }
                )
            }
        }

        if (likedTVShows.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
            item {
                Text(
                    text = "Liked TV Shows",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                )
            }

            item {
                StaticTVShowList(
                    tvShows = likedTVShows.map { it.toResultTVShow() },
                    onItemClick = { tvShow ->
                        navController.navigate("tvShowDetails/${tvShow.id}")
                    }
                )
            }
        }

        if (likedMovies.isEmpty() && likedTVShows.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillParentMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No liked movies or shows yet.")
                }
            }
        }
    }
}

