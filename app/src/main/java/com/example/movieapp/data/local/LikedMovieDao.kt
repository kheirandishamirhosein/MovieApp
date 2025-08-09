package com.example.movieapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LikedMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun likeMovie(movie: LikedMovieEntity)

    @Delete
    suspend fun unlikeMovie(movie: LikedMovieEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM liked_movies WHERE movieId = :movieId)")
    suspend fun isMovieLiked(movieId: Int): Boolean

}