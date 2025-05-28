package com.example.movieapp.presentation.upcoming

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.remote.model.movie.MovieResponse
import com.example.movieapp.data.remote.model.movie.ResultMovie
import com.example.movieapp.domain.usecase.upcoming.GetUpcomingMoviesUseCase
import com.example.movieapp.presentation.state.ResultStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpcomingMovieViewModel @Inject constructor(
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase
) : ViewModel() {

    private val _upcomingMoviesState =
        MutableStateFlow<ResultStates<List<ResultMovie>>>(ResultStates.Loading)
    val upcomingMoviesState: StateFlow<ResultStates<List<ResultMovie>>> = _upcomingMoviesState

    fun onEvent(event: UpcomingUiEvent) {
        when(event) {
            is UpcomingUiEvent.LoadUpcomingMovies -> {
                fetchUpcomingMovies()
            }
        }
    }

    private fun fetchUpcomingMovies() {
        viewModelScope.launch {
            _upcomingMoviesState.value = ResultStates.Loading
            _upcomingMoviesState.value = getUpcomingMoviesUseCase()
        }
    }

}

sealed class UpcomingUiEvent{
    data object LoadUpcomingMovies : UpcomingUiEvent()
}