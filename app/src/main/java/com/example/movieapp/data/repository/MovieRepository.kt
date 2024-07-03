package com.example.movieapp.data.repository

import android.util.Log
import com.example.movieapp.data.local.dao.MovieDao
import com.example.movieapp.data.local.entities.toEntity
import com.example.movieapp.data.remote.api.MovieApiService
import com.example.movieapp.doman.model.MovieResponse
import com.example.movieapp.doman.model.ResultMovie
import com.example.movieapp.presentation.state.ResultStates

class MovieRepository(
    private val apiService: MovieApiService,
    private val movieDao: MovieDao
) {

    suspend fun getPopularMovies(): ResultStates<MovieResponse> {
        return try {
            val movieResponse = apiService.getPopularMovies()
            Log.d("success getPopularMovies", movieResponse.toString())
            // convert MovieResponse to MovieEntity and save in Room db
            val movieEntities = movieResponse.results.map { it.toEntity() }
            movieDao.deleteAllMovies()
            movieDao.insertMovies(movieEntities)
            ResultStates.Success(movieResponse)
        } catch (e: Exception) {
            Log.e("error getPopularMovies", e.message.toString())
            //In case of error , return last data saved in room db
            val cachedMovies = movieDao.getAllMovies()
            if (cachedMovies.isNotEmpty()) {
                ResultStates.Success(
                    MovieResponse(
                        page = 1,
                        results = cachedMovies.map {
                            ResultMovie(
                                adult = false, // def
                                backdropPath = "", // def
                                genreIds = emptyList(), // def
                                id = it.id,
                                originalLanguage = "", // def
                                originalTitle = it.title,
                                overview = "", // def
                                popularity = 0.0, // def
                                posterPath = it.posterPath,
                                releaseDate = it.releaseDate,
                                title = it.title,
                                video = false, // def
                                voteAverage = it.voteAverage,
                                voteCount = 0 // def
                            )
                        },
                        totalPages = 1,
                        totalResults = cachedMovies.size
                    )
                )
            } else {
                ResultStates.Error(e)
            }
        }
    }
}
