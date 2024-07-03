package com.example.movieapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movieapp.data.remote.model.ResultMovie


@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    //val overview: String,
    val posterPath: String,
    //val backdropPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    //val voteCount: Int,
    //val originalLanguage: String
)

fun ResultMovie.toEntity(): MovieEntity {
    return MovieEntity(
        id = this.id,
        title = this.title,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        voteAverage = this.voteAverage
    )
}