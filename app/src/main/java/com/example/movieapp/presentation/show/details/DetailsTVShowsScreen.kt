package com.example.movieapp.presentation.show.details

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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import com.example.movieapp.data.remote.model.tvShow.ResultTVShow
import com.example.movieapp.data.remote.model.tvShow.details.TVShowCreditsResponse
import com.example.movieapp.presentation.show.lists.TVShowList
import com.example.movieapp.presentation.state.ResultStates
import com.example.movieapp.presentation.show.viewmodel.TvShowViewModel
import com.example.movieapp.presentation.show.viewmodel.TvShowsUiEvent
import com.example.movieapp.util.voteAverageFormatted
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTVShowsScreen(
    navController: NavController,
    tvShowId: Int,
    viewModel: TvShowViewModel = hiltViewModel()
) {

    val detailsTVShow by viewModel.tvShowDetailsState.collectAsState()
    val castState by viewModel.castState.collectAsState()
    val similarTVShow = viewModel.similarTVShows.collectAsLazyPagingItems()
    val isLiked by viewModel.isLiked.collectAsState()

    LaunchedEffect(tvShowId) {
        viewModel.onEvent(TvShowsUiEvent.LoadTvShowDetails(tvShowId))
        viewModel.onEvent(TvShowsUiEvent.LoadTVShowCredits(tvShowId))
        viewModel.onEvent(TvShowsUiEvent.LoadSimilarTVShows(tvShowId))
    }

    val isLoading = detailsTVShow is ResultStates.Loading ||
            castState is ResultStates.Loading
            ||
            similarTVShow.loadState.refresh is LoadState.Loading

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
                    TVShowDetailsSection(
                        tvShowDetailState = detailsTVShow,
                        isLiked = isLiked,
                        onLikeClick = {viewModel.toggleLike()}
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    CastSection(castState)
                    Spacer(modifier = Modifier.height(24.dp))
                    SimilarTVShowSection(
                        similarTVShow = similarTVShow,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun TVShowDetailsSection(
    tvShowDetailState: ResultStates<ResultTVShow>,
    isLiked: Boolean,
    onLikeClick: () -> Unit
) {
    val tvShow = (tvShowDetailState as ResultStates.Success).data
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
                    painter = rememberImagePainter("https://image.tmdb.org/t/p/w500${tvShow.backdropPath}"),
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
            text = tvShow.originalName,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

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

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = tvShow.overview,
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
            Text("Rating: ${tvShow.voteAverage.voteAverageFormatted()} (${tvShow.voteCount} votes)")
        }
    }
}


@Composable
fun CastSection(castState: ResultStates<TVShowCreditsResponse>) {
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
fun SimilarTVShowSection(
    similarTVShow: LazyPagingItems<ResultTVShow>,
    navController: NavController
) {
    Column {
        Text(
            text = "Similar TV Shows",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        when (similarTVShow.loadState.refresh) {
            is LoadState.Error -> {
                Text(
                    text = "Error loading similar tv Shows: " +
                            "${(similarTVShow.loadState.refresh as LoadState.Error).error.message}",
                    modifier = Modifier.padding(16.dp)
                )
            }

            else -> {
                TVShowList(
                    tvShows = similarTVShow,
                    onItemClick = { tvShow ->
                        navController.navigate("tvShowsDetail/${tvShow.id}")
                    }
                )
            }
        }
    }
}