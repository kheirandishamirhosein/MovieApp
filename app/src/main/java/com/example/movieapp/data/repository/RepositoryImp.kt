package com.example.movieapp.data.repository

import com.example.movieapp.data.remote.api.ApiService
import com.example.movieapp.util.apiWrapper
import com.example.movieapp.data.remote.model.movie.MovieResponse
import com.example.movieapp.data.remote.model.movie.ResultMovie
import com.example.movieapp.data.remote.model.tvShow.ResultTVShow
import com.example.movieapp.data.remote.model.tvShow.onTheAir.OnTheAirTVShowsResponse
import com.example.movieapp.data.remote.model.tvShow.popular.PopularTVShowResponse
import com.example.movieapp.data.remote.model.tvShow.topRated.TopRatedTVShowsResponse
import com.example.movieapp.data.remote.model.tvShow.trending.TrendingTVShowsResponse
import com.example.movieapp.domain.repository.Repository
import com.example.movieapp.presentation.state.ResultStates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    private val apiService: ApiService
) : Repository{

    override suspend fun getPopularMovies(): ResultStates<List<ResultMovie>> {
        return apiWrapper { apiService.getPopularMovies().results }
    }

    override suspend fun getMovieDetails(movieId: Int): ResultStates<ResultMovie> {
        return apiWrapper { apiService.getMovieDetails(movieId) }
    }

    override suspend fun getUpcomingMovies(): ResultStates<List<ResultMovie>> {
        return apiWrapper { apiService.getUpcomingMovies().results }
    }

    override suspend fun getPopularTVShows(): ResultStates<List<ResultTVShow>> {
        return apiWrapper { apiService.getPopularTVShows().results }
    }

    override suspend fun getTopRatedTVShows(): ResultStates<List<ResultTVShow>> {
        return apiWrapper { apiService.getTopRatedTVShows().results }
    }

    override suspend fun getOnTheAirTVShows(): ResultStates<List<ResultTVShow>> {
        return apiWrapper { apiService.getOnTheAirTVShows().results }
    }

    override suspend fun getTrendingTVShows(): ResultStates<List<ResultTVShow>> {
        return apiWrapper { apiService.getTrendingTVShows().results }
    }

    override suspend fun getTVShowDetails(tvId: Int): ResultStates<ResultTVShow> {
        return apiWrapper { apiService.getTVShowDetails(tvId) }
    }

}