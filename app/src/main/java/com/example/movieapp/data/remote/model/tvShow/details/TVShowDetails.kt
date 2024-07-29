package com.example.movieapp.data.remote.model.tvShow.details

import com.squareup.moshi.Json

data class TVShowDetails(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "overview") val overview: String,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "vote_average") val voteAverage: Float,
    @Json(name = "vote_count") val voteCount: Int,
    @Json(name = "first_air_date") val firstAirDate: String?,
    @Json(name = "last_air_date") val lastAirDate: String?,
    @Json(name = "number_of_episodes") val numberOfEpisodes: Int,
    @Json(name = "number_of_seasons") val numberOfSeasons: Int
)
