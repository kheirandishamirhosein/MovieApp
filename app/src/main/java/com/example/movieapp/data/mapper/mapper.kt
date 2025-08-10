package com.example.movieapp.data.mapper

import com.example.movieapp.data.local.entity.LikedItemEntity
import com.example.movieapp.data.remote.model.movie.ResultMovie
import com.example.movieapp.data.remote.model.tvShow.ResultTVShow

fun LikedItemEntity.toResultMovie(): ResultMovie {
    return ResultMovie(
        adult = false,
        backdropPath = null,
        genreIds = emptyList(),
        id = this.itemId,
        originalLanguage = "",
        originalTitle = this.title ?: "",
        overview = this.overview,
        popularity = 0.0,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        title = this.title ?: "",
        video = false,
        voteAverage = this.voteAverage,
        voteCount = 0
    )
}

fun LikedItemEntity.toResultTVShow(): ResultTVShow {
    return ResultTVShow(
        backdropPath = null,
        firstAirDate = this.releaseDate,
        genreIds = emptyList(),
        id = this.itemId,
        name = this.title ?: "",
        originCountry = emptyList(),
        originalLanguage = "",
        originalName = this.title ?: "",
        overview = this.overview,
        popularity = 0.0,
        posterPath = this.posterPath,
        voteAverage = this.voteAverage,
        voteCount = 0
    )
}
