package com.example.movieapp.presentation.ui.navigation.tvShow.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.movieapp.data.remote.model.tvShow.ResultTVShow
import com.example.movieapp.data.remote.model.tvShow.TVShowDetails

@OptIn(ExperimentalCoilApi::class)
@Composable
fun TVShowItem(tvShow: ResultTVShow, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Image(
            painter = rememberImagePainter("https://image.tmdb.org/t/p/w500${tvShow.posterPath}"),
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = tvShow.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text("Release Date: ${tvShow.firstAirDate}")
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Icon(
                    Icons.Default.Star,
                    contentDescription = "Vote",
                    tint = Color.Yellow
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Rating: ${tvShow.voteAverage}")
            }
        }
    }
}