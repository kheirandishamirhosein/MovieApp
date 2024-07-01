package com.example.movieapp.doman.model

import com.squareup.moshi.Json

data class MovieResponse(
   @Json(name = "page") val page: Int,
   @Json(name = "results") val results: List<ResultMovie>,
   @Json(name = "total_pages") val totalPages: Int,
   @Json(name = "total_results" ) val totalResults: Int
)