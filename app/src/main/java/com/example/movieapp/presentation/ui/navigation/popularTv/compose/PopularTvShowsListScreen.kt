package com.example.movieapp.presentation.ui.navigation.popularTv.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movieapp.data.remote.model.tvShow.TVShowResponse
import com.example.movieapp.presentation.state.ResultStates
import com.example.movieapp.presentation.ui.navigation.popularTv.viewmodel.TVShowsViewModel

@Composable
fun PopularTvShowsListScreen(viewModel: TVShowsViewModel = hiltViewModel()) {
    val popularTvShow by viewModel.tvShow.collectAsState()

    when (popularTvShow) {
        is ResultStates.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is ResultStates.Success -> {
            val tvShow = (popularTvShow as ResultStates.Success<TVShowResponse>).data.results
            LazyColumn {
                items(tvShow) { tv ->
                    TVShowItem(tvShow = tv) {
                        //TODO: For Navigate detail
                    }
                }
            }
        }

        is ResultStates.Error -> {
            Text("Error: ${(popularTvShow as ResultStates.Error).exception.message}")
        }
    }

}