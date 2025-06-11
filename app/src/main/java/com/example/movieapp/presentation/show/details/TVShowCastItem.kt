package com.example.movieapp.presentation.show.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.movieapp.data.remote.model.tvShow.details.TVShowCastMember

@Composable
fun CastList(
    castList: List<TVShowCastMember>,
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
    ) {
        items(items = castList.take(10)) { cast ->
            CastItem(cast = cast)
        }
    }
}

@Composable
fun CastItem(cast: TVShowCastMember) {
    Column(
        modifier = Modifier
            .width(140.dp)
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (cast.profilePath != null) {
            Image(
                painter = rememberImagePainter("https://image.tmdb.org/t/p/w185${cast.profilePath}"),
                contentDescription = cast.name ?: "",
                modifier = Modifier
                    .width(140.dp)
                    .height(180.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .width(140.dp)
                    .height(180.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Text("N/A", fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = cast.name ?: "",
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}