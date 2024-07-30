package com.example.movieapp.presentation.ui.navigation.tvShow.details.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import coil.compose.rememberImagePainter
import com.example.movieapp.data.remote.model.tvShow.ResultTVShow
import com.example.movieapp.presentation.state.ResultStates
import com.example.movieapp.presentation.ui.navigation.tvShow.details.viewmodel.DetailsTVShowsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTVShowsScreen(
    navController: NavController,
    movieId: Int,
    detailsTVShowSViewModel: DetailsTVShowsViewModel = hiltViewModel()
) {

    val detailsTVShow by detailsTVShowSViewModel.detailsTVShow.collectAsState()

    LaunchedEffect(movieId) {
        detailsTVShowSViewModel.fetchDetailTVShows(movieId)
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
            when (detailsTVShow) {
                is ResultStates.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is ResultStates.Success -> {
                    val tvShow = (detailsTVShow as ResultStates.Success<ResultTVShow>).data
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        item {
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                            ) {
                                Image(
                                    painter = rememberImagePainter("https://image.tmdb.org/t/p/w500${tvShow.backdropPath}"),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = tvShow.originalName,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Release Date: ${tvShow.firstAirDate}",
                                    fontSize = 14.sp,
                                    color = Color.Magenta
                                )
                                Text(
                                    text = "Popularity: ${tvShow.popularity}",
                                    fontSize = 14.sp,
                                    color = Color.Magenta
                                )
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = tvShow.overview,
                                fontSize = 16.sp
                            )
                        }
                        item {
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
                                Text("Rating: ${tvShow.voteAverage} (${tvShow.voteCount} votes)")
                            }
                        }
                    }
                }

                is ResultStates.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Text("Error: ${(detailsTVShow as ResultStates.Error).exception.message}")
                    }
                }
            }
        }
    }

}