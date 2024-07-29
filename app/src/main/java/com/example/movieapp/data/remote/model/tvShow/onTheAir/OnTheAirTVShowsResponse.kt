package com.example.movieapp.data.remote.model.tvShow.onTheAir

import com.example.movieapp.data.remote.model.tvShow.ResultTVShow
import com.squareup.moshi.Json

data class OnTheAirTVShowsResponse(
    val page: Int,
    val results: List<ResultTVShow>,
    @Json(name = "total_pages") val totalPages: Int,
    @Json(name = "total_results") val totalResults: Int
)