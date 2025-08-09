package com.example.movieapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "liked_items")
data class LikedItemEntity(
    @PrimaryKey val itemId: Int,
    val type: String
)