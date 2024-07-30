package com.example.movieapp.data.remote.model.tvShow.trending

import com.example.movieapp.data.remote.model.tvShow.ResultTVShow
import com.squareup.moshi.Json

data class TrendingTVShowsResponse(
    @Json(name = "page") val page: Int,
    @Json(name = "results") val results: List<ResultTVShow>,
    @Json(name = "total_pages") val totalPages: Int,
    @Json(name = "total_results") val totalResults: Int
)