package com.example.movieapp.presentation.ui.navigation.uncoming.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.remote.model.MovieResponse
import com.example.movieapp.data.remote.model.ResultMovie
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.presentation.state.ResultStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpcomingMovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _upcomingMovies =
        MutableStateFlow<ResultStates<MovieResponse>>(ResultStates.Loading)
    val upcomingMovies: StateFlow<ResultStates<MovieResponse>> = _upcomingMovies

    fun fetchUpcomingMovies() {
        viewModelScope.launch {
            val result = movieRepository.getUpcomingMovies()
            _upcomingMovies.value = result
        }
    }

}