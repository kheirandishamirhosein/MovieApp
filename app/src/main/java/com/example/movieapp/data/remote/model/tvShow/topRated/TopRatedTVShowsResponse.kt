package com.example.movieapp.data.remote.model.tvShow.topRated

import com.example.movieapp.data.remote.model.tvShow.ResultTVShow
import com.squareup.moshi.Json

data class TopRatedTVShowsResponse(
    val page: Int,
    val results: List<ResultTVShow>,
    @Json(name = "total_pages") val totalPages: Int,
    @Json(name = "total_results") val totalResults: Int
)
