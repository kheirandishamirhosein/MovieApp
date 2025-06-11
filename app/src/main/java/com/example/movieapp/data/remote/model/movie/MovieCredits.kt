package com.example.movieapp.data.remote.model.movie

import com.squareup.moshi.Json

data class MovieCreditsResponse(
    @Json(name = "id") val id: Int?,
    @Json(name = "cast") val cast: List<CastMember>?
)

data class CastMember(
    @Json(name = "id") val id: Int?,
    @Json(name = "name") val name: String?,
    @Json(name = "character") val character: String?,
    @Json(name = "profile_path") val profilePath: String?
)