package com.example.movieapp.presentation.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movieapp.data.remote.model.tvShow.ResultTVShow
import com.example.movieapp.presentation.show.lists.TVShowItem

@Composable
fun StaticTVShowList(
    tvShows: List<ResultTVShow>,
    onItemClick: (ResultTVShow) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(tvShows.size) { index ->
            val tvShow = tvShows[index]
            TVShowItem(tvShow = tvShow, onClick = { onItemClick(tvShow) })
        }
    }
}