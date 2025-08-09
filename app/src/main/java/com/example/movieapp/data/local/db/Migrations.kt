package com.example.movieapp.data.local.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // rename table
        database.execSQL("ALTER TABLE liked_movies RENAME TO liked_items")
        // add column type
        database.execSQL("ALTER TABLE liked_items ADD COLUMN type TEXT NOT NULL DEFAULT 'movie'")

    }
}