package com.example.movieapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapp.data.local.entity.LikedItemEntity

@Dao
interface LikedItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun likeItem(item: LikedItemEntity)

    @Delete
    suspend fun unlikeItem(item: LikedItemEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM liked_items WHERE itemId = :itemId AND type = :type)")
    suspend fun isItemLiked(itemId: Int, type: String): Boolean
}