package com.example.movieapp.di

import com.example.movieapp.data.remote.api.ApiService
import com.example.movieapp.data.repository.Repository
import com.example.movieapp.util.NetworkUtils
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideMovieApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(apiService: ApiService): Repository {
        return Repository(apiService)
    }

}