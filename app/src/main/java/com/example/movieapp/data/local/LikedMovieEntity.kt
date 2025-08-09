package com.example.movieapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "liked_movies")
data class LikedMovieEntity(
    @PrimaryKey val movieId: Int
)