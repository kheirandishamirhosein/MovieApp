package com.example.movieapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieapp.data.local.dao.MovieDao
import com.example.movieapp.data.local.entities.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class MovieDb : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}