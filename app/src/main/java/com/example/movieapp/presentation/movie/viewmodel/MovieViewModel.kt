package com.example.movieapp.presentation.movie.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.remote.model.movie.ResultMovie
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
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {

    private val _popularMoviesState = MutableStateFlow<ResultStates<List<ResultMovie>>>(ResultStates.Loading)
    val popularMoviesState: StateFlow<ResultStates<List<ResultMovie>>> = _popularMoviesState

    private val _movieDetailState = MutableStateFlow<ResultStates<ResultMovie>>(ResultStates.Loading)
    val movieDetailState: StateFlow<ResultStates<ResultMovie>> = _movieDetailState

    fun onEvent(event: MovieUiEvent) {
        when (event) {
            is MovieUiEvent.LoadPopularMovies -> {
                fetchPopularMovies()
            }
            is MovieUiEvent.LoadMovieDetails -> {
                fetchMovieDetails(event.movieId)
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

}

sealed class MovieUiEvent {
    data object LoadPopularMovies : MovieUiEvent()
    data class LoadMovieDetails(val movieId: Int) : MovieUiEvent()
}