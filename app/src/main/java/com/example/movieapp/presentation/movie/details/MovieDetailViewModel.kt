package com.example.movieapp.presentation.movie.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.remote.model.movie.ResultMovie
import com.example.movieapp.data.repository.RepositoryImp
import com.example.movieapp.presentation.state.ResultStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repositoryImp: RepositoryImp
) : ViewModel() {

    private val _movieDetail = MutableStateFlow<ResultStates<ResultMovie>>(ResultStates.Loading)
    val movieDetail: StateFlow<ResultStates<ResultMovie>> get() = _movieDetail

    fun fetchMovieDetails(movieId: Int) {
        viewModelScope.launch {
            _movieDetail.value = ResultStates.Loading
            repositoryImp.getMovieDetails(movieId)
                .collect { result ->
                    _movieDetail.value = result
                }
        }
    }

}