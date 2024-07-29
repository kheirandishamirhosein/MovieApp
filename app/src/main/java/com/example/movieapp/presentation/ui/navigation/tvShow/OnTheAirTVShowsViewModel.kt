package com.example.movieapp.presentation.ui.navigation.tvShow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.remote.model.tvShow.onTheAir.OnTheAirTVShowsResponse
import com.example.movieapp.data.repository.Repository
import com.example.movieapp.presentation.state.ResultStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnTheAirTVShowsViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {

    private val _onTheAir =
        MutableStateFlow<ResultStates<OnTheAirTVShowsResponse>>(ResultStates.Loading)
    val onTheAir: StateFlow<ResultStates<OnTheAirTVShowsResponse>> = _onTheAir

    init {
        fetchOnTheAirTVShows()
    }

    private fun fetchOnTheAirTVShows() {
        viewModelScope.launch {
            repository.getOnTheAirTVShows()
                .collect { result ->
                    _onTheAir.value = result
                }
        }
    }

}