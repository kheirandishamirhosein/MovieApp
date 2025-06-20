package com.example.movieapp.presentation.movie.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.remote.model.movie.CastMember
import com.example.movieapp.data.remote.model.movie.MovieCreditsResponse
import com.example.movieapp.data.remote.model.movie.ResultMovie
import com.example.movieapp.domain.usecase.movie.GetMovieCreditsUseCase
import com.example.movieapp.domain.usecase.movie.GetMovieDetailsUseCase
import com.example.movieapp.domain.usecase.movie.PopularMovieListUseCase
import com.example.movieapp.presentation.state.ResultStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getPopularMovieListUseCase: PopularMovieListUseCase,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieCreditsUseCase: GetMovieCreditsUseCase
) : ViewModel() {

    private val _popularMoviesState = MutableStateFlow<ResultStates<List<ResultMovie>>>(ResultStates.Loading)
    val popularMoviesState: StateFlow<ResultStates<List<ResultMovie>>> = _popularMoviesState

    private val _movieDetailState = MutableStateFlow<ResultStates<ResultMovie>>(ResultStates.Loading)
    val movieDetailState: StateFlow<ResultStates<ResultMovie>> = _movieDetailState

    private val _castState = MutableStateFlow<ResultStates<MovieCreditsResponse>>(ResultStates.Loading)
    val castState: StateFlow<ResultStates<MovieCreditsResponse>> = _castState


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

}

sealed class MovieUiEvent {
    data object LoadPopularMovies : MovieUiEvent()
    data class LoadMovieDetails(val movieId: Int) : MovieUiEvent()
    data class LoadMovieCredits(val movieId: Int) : MovieUiEvent()
}