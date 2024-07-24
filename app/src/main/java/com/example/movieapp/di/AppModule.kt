package com.example.movieapp.di

import android.content.Context
import androidx.room.Room
import com.example.movieapp.data.local.dao.MovieDao
import com.example.movieapp.data.local.database.MovieDb
import com.example.movieapp.data.remote.api.MovieApiService
import com.example.movieapp.data.repository.Repository
import com.example.movieapp.util.NetworkUtils
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NetworkUtils.BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieApiService(retrofit: Retrofit): MovieApiService {
        return retrofit.create(MovieApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): MovieDb {
        return Room.databaseBuilder(
            appContext,
            MovieDb::class.java,
            "movie-database",
        ).build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(db: MovieDb): MovieDao {
        return db.movieDao()
    }

    @Provides
    @Singleton
    fun provideMovieRepository(apiService: MovieApiService, movieDao: MovieDao): Repository {
        return Repository(apiService, movieDao)
    }

}