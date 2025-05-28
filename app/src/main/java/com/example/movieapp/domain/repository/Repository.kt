package com.example.movieapp.domain.repository

import com.example.movieapp.data.remote.model.movie.ResultMovie
import com.example.movieapp.data.remote.model.tvShow.ResultTVShow
import com.example.movieapp.presentation.state.ResultStates

interface Repository {
    suspend fun getPopularMovies(): ResultStates<List<ResultMovie>>
    suspend fun getMovieDetails(movieId: Int): ResultStates<ResultMovie>
    suspend fun getUpcomingMovies(): ResultStates<List<ResultMovie>>

    suspend fun getPopularTVShows(): ResultStates<List<ResultTVShow>>
    suspend fun getTopRatedTVShows(): ResultStates<List<ResultTVShow>>
    suspend fun getOnTheAirTVShows(): ResultStates<List<ResultTVShow>>
    suspend fun getTrendingTVShows(): ResultStates<List<ResultTVShow>>
    suspend fun getTVShowDetails(tvId: Int): ResultStates<ResultTVShow>
}