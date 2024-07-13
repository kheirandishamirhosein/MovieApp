package com.example.movieapp.presentation.ui.navigation.popularMovie.movieList.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.data.remote.model.MovieResponse
import com.example.movieapp.presentation.state.ResultStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movies = MutableStateFlow<ResultStates<MovieResponse>>(ResultStates.Loading)
    val movies: StateFlow<ResultStates<MovieResponse>> get() = _movies

    fun fetchPopularMovies() {
        _movies.value = ResultStates.Loading
        viewModelScope.launch {
            val result = repository.getPopularMovies()
            _movies.value = result
        }
    }

}