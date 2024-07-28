package com.example.movieapp.data.repository

import com.example.movieapp.data.local.dao.MovieDao
import com.example.movieapp.data.local.entities.toEntity
import com.example.movieapp.data.remote.api.MovieApiService
import com.example.movieapp.data.remote.api.apiWrapper
import com.example.movieapp.data.remote.model.movie.MovieResponse
import com.example.movieapp.data.remote.model.movie.ResultMovie
import com.example.movieapp.data.remote.model.tvShow.TVShowDetails
import com.example.movieapp.data.remote.model.tvShow.TVShowResponse
import com.example.movieapp.data.remote.model.tvShow.topRated.TopRatedTVShowsResponse
import com.example.movieapp.presentation.state.ResultStates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiService: MovieApiService,
    private val movieDao: MovieDao
) {

    suspend fun getPopularMovies(): Flow<ResultStates<MovieResponse>> = flow {
        emit(ResultStates.Loading)
        val result = apiWrapper {
            val movieResponse = apiService.getPopularMovies()
            val movieEntities = movieResponse.results.map { it.toEntity() }
            movieDao.deleteAllMovies()
            movieDao.insertMovies(movieEntities)
            movieResponse
        }
        emit(result)
        if (result is ResultStates.Error) {
            val cachedMovies = movieDao.getAllMovies()
            if (cachedMovies.isNotEmpty()) {
                emit(
                    ResultStates.Success(
                        MovieResponse(
                            page = 1,
                            results = cachedMovies.map {
                                ResultMovie(
                                    adult = false,
                                    backdropPath = "",
                                    genreIds = emptyList(),
                                    id = it.id,
                                    originalLanguage = "",
                                    originalTitle = it.title,
                                    overview = "",
                                    popularity = 0.0,
                                    posterPath = it.posterPath,
                                    releaseDate = it.releaseDate,
                                    title = it.title,
                                    video = false,
                                    voteAverage = it.voteAverage,
                                    voteCount = 0
                                )
                            },
                            totalPages = 1,
                            totalResults = cachedMovies.size
                        )
                    )
                )
            }
        }
    }


    suspend fun getMovieDetails(movieId: Int): Flow<ResultStates<ResultMovie>> = flow {
        emit(ResultStates.Loading)
        val response = apiWrapper { apiService.getMovieDetails(movieId) }
        emit(response)
    }

    suspend fun getUpcomingMovies(): Flow<ResultStates<MovieResponse>> = flow {
        emit(ResultStates.Loading)
        val response = apiWrapper { apiService.getUpcomingMovies() }
        emit(response)
    }

    suspend fun getPopularTVShows(): Flow<ResultStates<TVShowResponse>> = flow {
        emit(ResultStates.Loading)
        val response = apiWrapper { apiService.getPopularTVShows() }
        emit(response)
    }

    suspend fun getTopRatedTVShows(): Flow<ResultStates<TopRatedTVShowsResponse>> = flow {
        emit(ResultStates.Loading)
        val response = apiWrapper { apiService.getTopRatedTVShows() }
        emit(response)
    }

}