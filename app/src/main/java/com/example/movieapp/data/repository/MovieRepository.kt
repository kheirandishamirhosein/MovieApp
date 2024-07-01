package com.example.movieapp.data.repository

import com.example.movieapp.data.remote.api.MovieApiService
import com.example.movieapp.doman.model.MovieResponse
import com.example.movieapp.presentation.state.ResultStates

class MovieRepository(private val apiService: MovieApiService) {

    suspend fun getPopularMovies(): ResultStates<MovieResponse> {
        return try {
            val response = apiService.getPopularMovies()
            if (response.isSuccessful) {
                ResultStates.Success(response.body()!!)
            } else {
                ResultStates.Error(Exception("Error : ${response.message()}"))
            }
        } catch (ex: Exception) {
            ResultStates.Error(ex)
        }
    }

}