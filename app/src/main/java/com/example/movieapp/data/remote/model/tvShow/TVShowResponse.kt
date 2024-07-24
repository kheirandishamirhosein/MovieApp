package com.example.movieapp.data.remote.model.tvShow

import com.squareup.moshi.Json

data class TVShowResponse(
    @Json(name = "page") val page: Int,
    @Json(name = "results") val results: List<ResultTVShow>,
    @Json(name = "total_pages") val totalPages: Int,
    @Json(name = "total_results") val totalResults: Int
)
