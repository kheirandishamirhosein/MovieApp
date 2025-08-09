package com.example.movieapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieapp.data.local.dao.LikedItemDao
import com.example.movieapp.data.local.entity.LikedItemEntity

@Database(entities = [LikedItemEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun likedItemDao(): LikedItemDao
}