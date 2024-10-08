package com.example.movieapp.presentation.tv.lists

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
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
import androidx.navigation.NavController
import com.example.movieapp.data.remote.model.tvShow.ResultTVShow
import com.example.movieapp.data.remote.model.tvShow.onTheAir.OnTheAirTVShowsResponse
import com.example.movieapp.data.remote.model.tvShow.popular.PopularTVShowResponse
import com.example.movieapp.data.remote.model.tvShow.topRated.TopRatedTVShowsResponse
import com.example.movieapp.data.remote.model.tvShow.trending.TrendingTVShowsResponse
import com.example.movieapp.presentation.state.ResultStates
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TvShowsListScreen(
    navController: NavController,
    popularTVShowsViewModel: PopularTVShowsViewModel = hiltViewModel(),
    topRatedTVShowsViewModel: TopRatedTVShowsViewModel = hiltViewModel(),
    onTheAirTVShowsViewModel: OnTheAirTVShowsViewModel = hiltViewModel(),
    trendingTVShowsViewModel: TrendingTVShowsViewModel = hiltViewModel()
) {
    val popularTvShows by popularTVShowsViewModel.tvShow.collectAsState()
    val topRatedTVShows by topRatedTVShowsViewModel.topRatedTVShow.collectAsState()
    val onTheAirTVShows by onTheAirTVShowsViewModel.onTheAir.collectAsState()
    val trendingTVShows by trendingTVShowsViewModel.trending.collectAsState()

    val isLoading =
        popularTvShows is ResultStates.Loading &&
                topRatedTVShows is ResultStates.Loading &&
                onTheAirTVShows is ResultStates.Loading &&
                trendingTVShows is ResultStates.Loading

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "TV Shows") })
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier.padding(paddingValues)
            ) {
                item {
                    if (isLoading) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    } else {

                        when (onTheAirTVShows) {
                            is ResultStates.Success -> {
                                val onTheAirTVShowList =
                                    (onTheAirTVShows as ResultStates.Success<OnTheAirTVShowsResponse>).data.results
                                val topOnTheAir =
                                    onTheAirTVShowList.sortedByDescending { it.voteAverage }
                                        .take(10)
                                TVShowCarousel(tvShow = topOnTheAir)
                            }

                            is ResultStates.Error -> {
                                Text(text = "Error: ${(onTheAirTVShows as ResultStates.Error).exception.message}")
                            }

                            else -> {}
                        }

                        when (trendingTVShows) {
                            is ResultStates.Success -> {
                                val trendingTVShowList =
                                    (trendingTVShows as ResultStates.Success<TrendingTVShowsResponse>).data.results

                                Text(
                                    text = "Trending TV Shows",
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            top = 16.dp,
                                            bottom = 8.dp,
                                            start = 16.dp,
                                            end = 16.dp
                                        )
                                )
                                TVShowList(tvShows = trendingTVShowList) { tv ->
                                    navController.navigate("tvShowsDetail/${tv.id}")
                                }
                            }

                            is ResultStates.Error -> {
                                Text("Error: ${(trendingTVShows as ResultStates.Error).exception.message}")
                            }

                            else -> {}
                        }

                        when (topRatedTVShows) {
                            is ResultStates.Success -> {
                                val topRated =
                                    (topRatedTVShows as ResultStates.Success<TopRatedTVShowsResponse>).data.results

                                Text(
                                    text = "Top Rated TV Shows",
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            top = 16.dp,
                                            bottom = 8.dp,
                                            start = 16.dp,
                                            end = 16.dp
                                        )
                                )
                                TVShowList(tvShows = topRated) { tv ->
                                    navController.navigate("tvShowsDetail/${tv.id}")
                                }
                            }

                            is ResultStates.Error -> {
                                Text("Error: ${(topRatedTVShows as ResultStates.Error).exception.message}")
                            }

                            else -> {}
                        }

                        when (popularTvShows) {
                            is ResultStates.Success -> {
                                val popularTvShowList =
                                    (popularTvShows as ResultStates.Success<PopularTVShowResponse>).data.results

                                Text(
                                    text = "Popular TV Shows",
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            top = 16.dp,
                                            bottom = 8.dp,
                                            start = 16.dp,
                                            end = 16.dp
                                        )
                                )
                                TVShowList(tvShows = popularTvShowList) { tv ->
                                    navController.navigate("tvShowsDetail/${tv.id}")
                                }
                            }

                            is ResultStates.Error -> {
                                Text("Error: ${(popularTvShows as ResultStates.Error).exception.message}")
                            }

                            else -> {}
                        }

                    }
                }
            }
        }
    )
}

@Composable
fun TVShowList(tvShows: List<ResultTVShow>, onItemClick: (ResultTVShow) -> Unit) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(tvShows) { tv ->
            TVShowItem(tvShow = tv, onClick = { onItemClick(tv) })
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