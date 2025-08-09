package com.example.movieapp.di

import android.content.Context
import androidx.room.Room
import com.example.movieapp.data.local.dao.LikedItemDao
import com.example.movieapp.data.local.db.AppDatabase
import com.example.movieapp.data.local.db.MIGRATION_1_2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).addMigrations(MIGRATION_1_2)
            .build()
    }

    @Provides
    fun provideLikedMovieDao(appDatabase: AppDatabase): LikedItemDao {
        return appDatabase.likedItemDao()
    }

}