package com.example.movieapp.presentation.movie.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.data.remote.model.movie.MovieCreditsResponse
import com.example.movieapp.data.remote.model.movie.ResultMovie
import com.example.movieapp.domain.usecase.movie.GetMovieCreditsUseCase
import com.example.movieapp.domain.usecase.movie.GetMovieDetailsUseCase
import com.example.movieapp.domain.usecase.movie.GetNowPlayingMoviesUseCase
import com.example.movieapp.domain.usecase.movie.GetSimilarMoviesUseCase
import com.example.movieapp.domain.usecase.movie.GetTopRatedMoviesUseCase
import com.example.movieapp.domain.usecase.movie.GetTrendingMoviesUseCase
import com.example.movieapp.domain.usecase.movie.GetUpcomingMoviesUseCase
import com.example.movieapp.domain.usecase.movie.PopularMovieListUseCase
import com.example.movieapp.presentation.state.ResultStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getPopularMovieListUseCase: PopularMovieListUseCase,
    getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieCreditsUseCase: GetMovieCreditsUseCase
) : ViewModel() {

    private val _popularMoviesState = MutableStateFlow<ResultStates<List<ResultMovie>>>(ResultStates.Loading)
    val popularMoviesState: StateFlow<ResultStates<List<ResultMovie>>> = _popularMoviesState

    val nowPlayingMovies: Flow<PagingData<ResultMovie>> =
        getNowPlayingMoviesUseCase().cachedIn(viewModelScope)

    val topRatedMovies: Flow<PagingData<ResultMovie>> =
        getTopRatedMoviesUseCase().cachedIn(viewModelScope)

    val trendingMovies: Flow<PagingData<ResultMovie>> =
        getTrendingMoviesUseCase().cachedIn(viewModelScope)

    val upcomingMovies: Flow<PagingData<ResultMovie>> =
        getUpcomingMoviesUseCase().cachedIn(viewModelScope)

    private val _movieDetailState = MutableStateFlow<ResultStates<ResultMovie>>(ResultStates.Loading)
    val movieDetailState: StateFlow<ResultStates<ResultMovie>> = _movieDetailState

    private val _castState = MutableStateFlow<ResultStates<MovieCreditsResponse>>(ResultStates.Loading)
    val castState: StateFlow<ResultStates<MovieCreditsResponse>> = _castState

    private val _similarMovieId = MutableStateFlow<Int?>(null)
    val similarMovies: Flow<PagingData<ResultMovie>> = _similarMovieId
        .filterNotNull()
        .flatMapLatest { movieId ->
            getSimilarMoviesUseCase(movieId)
        }
        .cachedIn(viewModelScope)

    private val _isLiked = MutableStateFlow(false)
    val isLiked: StateFlow<Boolean> = _isLiked

    fun onEvent(event: MovieUiEvent) {
        when (event) {
            is MovieUiEvent.LoadPopularMovies -> {
                fetchPopularMovies()
            }

            is MovieUiEvent.LoadMovieDetails -> {
                fetchMovieDetails(event.movieId)
            }

            is MovieUiEvent.LoadMovieCredits -> {
                fetchMovieCredits(event.movieId)
            }

            is MovieUiEvent.LoadSimilarMovies -> {
                fetchSimilarMovies(event.movieId)
            }

            else -> Unit
        }
    }

    private fun fetchPopularMovies() {
        viewModelScope.launch {
            _popularMoviesState.value = ResultStates.Loading
            _popularMoviesState.value = getPopularMovieListUseCase()
        }
    }

    private fun fetchMovieDetails(movieId: Int) {
        viewModelScope.launch {
            _movieDetailState.value = ResultStates.Loading
            _movieDetailState.value = getMovieDetailsUseCase(movieId)
        }
    }

    private fun fetchMovieCredits(movieId: Int) {
        viewModelScope.launch {
            _castState.value = ResultStates.Loading
            _castState.value = getMovieCreditsUseCase(movieId)
        }
    }

    private fun fetchSimilarMovies(movieId: Int) {
        _similarMovieId.value = movieId
    }

    fun toggleLike() {
        _isLiked.value = !_isLiked.value
    }

}

sealed class MovieUiEvent {
    data object LoadPopularMovies : MovieUiEvent()
    data object LoadNowPlayingMovies : MovieUiEvent()
    data object LoadTopRatedMovies : MovieUiEvent()
    data object LoadTrendingMovies : MovieUiEvent()
    data object LoadUpcomingMovies: MovieUiEvent()
    data class LoadSimilarMovies(val movieId: Int) : MovieUiEvent()
    data class LoadMovieDetails(val movieId: Int) : MovieUiEvent()
    data class LoadMovieCredits(val movieId: Int) : MovieUiEvent()
}