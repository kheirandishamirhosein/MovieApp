package com.example.movieapp.data.repository

import com.example.movieapp.data.local.dao.MovieDao
import com.example.movieapp.data.local.entities.toEntity
import com.example.movieapp.data.remote.api.MovieApiService
import com.example.movieapp.data.remote.api.apiWrapper
import com.example.movieapp.data.remote.model.MovieResponse
import com.example.movieapp.data.remote.model.ResultMovie
import com.example.movieapp.presentation.state.ResultStates

class MovieRepository(
    private val apiService: MovieApiService,
    private val movieDao: MovieDao
) {

    suspend fun getPopularMovies(): ResultStates<MovieResponse> {
        return apiWrapper {
            val movieResponse = apiService.getPopularMovies()
            // // convert MovieResponse to MovieEntity and save in Room db
            val movieEntities = movieResponse.results.map { it.toEntity() }
            movieDao.deleteAllMovies()
            movieDao.insertMovies(movieEntities)
            movieResponse
        }.also { result ->
            if (result is ResultStates.Error) {
                //In case of error , return last data saved in room db
                val cachedMovies = movieDao.getAllMovies()
                if (cachedMovies.isNotEmpty()) {
                    return ResultStates.Success(
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
                }
            }
        }
    }

    suspend fun getMovieDetails(movieId: Int): ResultStates<ResultMovie> {
        return apiWrapper {
            apiService.getMovieDetails(movieId)
        }
    }

    suspend fun getUpcomingMovies(): ResultStates<MovieResponse> {
        return apiWrapper {
            apiService.getUpcomingMovies()
        }
    }

}