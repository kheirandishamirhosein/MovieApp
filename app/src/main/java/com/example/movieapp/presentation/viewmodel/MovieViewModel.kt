package com.example.movieapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.doman.model.MovieResponse
import com.example.movieapp.presentation.state.ResultStates
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

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