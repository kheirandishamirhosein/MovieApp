package com.example.movieapp.presentation.ui.navigation.tvShow.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movieapp.data.remote.model.tvShow.ResultTVShow
import com.example.movieapp.data.remote.model.tvShow.TVShowDetails
import com.example.movieapp.data.remote.model.tvShow.TVShowResponse
import com.example.movieapp.presentation.state.ResultStates
import com.example.movieapp.presentation.ui.navigation.tvShow.popular.viewmodel.TVShowsViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopularTvShowsListScreen(
    tvShowsViewModel: TVShowsViewModel = hiltViewModel(),
) {
    val popularTvShows by tvShowsViewModel.tvShow.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Popular TVShows") })
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {

                when (popularTvShows) {
                    is ResultStates.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is ResultStates.Success -> {
                        val popularTvShowList =
                            (popularTvShows as ResultStates.Success<TVShowResponse>).data.results

                        val topTVShow =
                            popularTvShowList.sortedByDescending { it.voteAverage }.take(6)

                        TVShowCarousel(tvShow = topTVShow)
                        Text(
                            text = "Popular TV Show",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Start)
                                .padding(top = 16.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
                        )
                        TVShowList(tvShows = popularTvShowList)
                    }

                    is ResultStates.Error -> {
                        Text("Error: ${(popularTvShows as ResultStates.Error).exception.message}")
                    }
                }

            }
        }
    )
}

@Composable
fun TVShowList(tvShows: List<ResultTVShow>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(tvShows) { tv ->
            TVShowItem(tvShow = tv) {
                // TODO: For Navigate detail
            }
        }
    }
}

@Composable
fun TVShowCarousel(tvShow: List<ResultTVShow>) {
    var currentIndex by remember { mutableIntStateOf(0) }
    LaunchedEffect(currentIndex) {
        delay(5000)
        currentIndex = (currentIndex + 1) % tvShow.size
    }
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        tvShow.forEachIndexed { index, tv ->
            AnimatedVisibility(
                visible = index == currentIndex,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                TVShowCard(tvShow = tv)
            }
        }
    }
}