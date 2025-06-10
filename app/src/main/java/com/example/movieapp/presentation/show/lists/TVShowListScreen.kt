package com.example.movieapp.presentation.show.lists

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movieapp.data.remote.model.tvShow.ResultTVShow
import com.example.movieapp.presentation.state.ResultStates
import com.example.movieapp.presentation.show.viewmodel.TvShowViewModel
import com.example.movieapp.presentation.show.viewmodel.TvShowsUiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TvShowsListScreen(
    navController: NavController,
    viewModel: TvShowViewModel = hiltViewModel(),
) {
    val popularTvShows = viewModel.popularTVShows.collectAsLazyPagingItems()
    val topRatedTVShows = viewModel.topRatedTVShowsPaging.collectAsLazyPagingItems()
    val trendingTVShows = viewModel.trendingTVShowsPaging.collectAsLazyPagingItems()
    val onTheAirTVShows by viewModel.onTheAirTVShows.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(TvShowsUiEvent.LoadPopularTvShows)
        viewModel.onEvent(TvShowsUiEvent.LoadTopRatedTVShows)
        viewModel.onEvent(TvShowsUiEvent.LoadTrendingTVShows)
        viewModel.onEvent(TvShowsUiEvent.LoadOnTheAirTVShows)
    }

    val isLoading =
        popularTvShows.loadState.refresh is LoadState.Loading &&
                topRatedTVShows.loadState.refresh is LoadState.Loading &&
                trendingTVShows.loadState.refresh is LoadState.Loading &&
                onTheAirTVShows is ResultStates.Loading
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "TV Shows") })
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
                        when (onTheAirTVShows) {
                            is ResultStates.Success -> {
                                val onTheAirTVShowList =
                                    (onTheAirTVShows as ResultStates.Success<List<ResultTVShow>>).data
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

                        TVShowSection(
                            title = "Trending TV Shows",
                            tvShows = trendingTVShows,
                            onItemClick = { tv -> navController.navigate("tvShowsDetail/${tv.id}") }
                        )

                        TVShowSection(
                            title = "Top Rated TV Shows",
                            tvShows = topRatedTVShows,
                            onItemClick = { tv -> navController.navigate("tvShowsDetail/${tv.id}") }
                        )

                        TVShowSection(
                            title = "Popular TV Shows",
                            tvShows = popularTvShows,
                            onItemClick = { tv -> navController.navigate("tvShowsDetail/${tv.id}") }
                        )
                    }
                }
            }
        }
    )
}


@Composable
fun TVShowSection(
    title: String,
    tvShows: LazyPagingItems<ResultTVShow>,
    onItemClick: (ResultTVShow) -> Unit
) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
    )

    when (val refreshLoadState = tvShows.loadState.refresh) {
        is LoadState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is LoadState.Error -> {
            Text(
                text = "Error loading $title: ${refreshLoadState.error.message}",
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
        }
        else -> {
            TVShowList(tvShows = tvShows, onItemClick = onItemClick)
        }
    }
}
