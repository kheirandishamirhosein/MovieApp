package com.example.movieapp.presentation.ui.navigation.popularMovie.details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.remote.model.ResultMovie
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.presentation.state.ResultStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movieDetail = MutableStateFlow<ResultStates<ResultMovie>>(ResultStates.Loading)
    val movieDetail: StateFlow<ResultStates<ResultMovie>> get() = _movieDetail

    fun fetchMovieDetails(movieId: Int) {
        _movieDetail.value = ResultStates.Loading
        viewModelScope.launch {
            val result = repository.getMovieDetails(movieId)
            _movieDetail.value = result
        }
    }

}