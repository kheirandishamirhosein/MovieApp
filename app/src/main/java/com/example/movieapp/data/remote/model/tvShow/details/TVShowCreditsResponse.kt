package com.example.movieapp.data.remote.model.tvShow.details

import com.squareup.moshi.Json

data class TVShowCreditsResponse(
    @Json(name = "id") val id: Int?,
    @Json(name = "cast") val cast: List<TVShowCastMember>?
)

data class TVShowCastMember(
    @Json(name = "id") val id: Int?,
    @Json(name = "name") val name: String?,
    @Json(name = "character") val character: String?,
    @Json(name = "profile_path") val profilePath: String?
)