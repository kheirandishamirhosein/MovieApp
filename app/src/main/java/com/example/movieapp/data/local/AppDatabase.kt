package com.example.movieapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LikedMovieEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun likedMovieDao(): LikedMovieDao
}