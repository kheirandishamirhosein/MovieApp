package com.example.movieapp.presentation.ui.navigation.tvShow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.remote.model.tvShow.topRated.TopRatedTVShowsResponse
import com.example.movieapp.data.repository.Repository
import com.example.movieapp.presentation.state.ResultStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopRatedTVShowsViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {

    private val _topRatedTVShow =
        MutableStateFlow<ResultStates<TopRatedTVShowsResponse>>(ResultStates.Loading)
    val topRatedTVShow: StateFlow<ResultStates<TopRatedTVShowsResponse>> = _topRatedTVShow

    init {
        fetchTopRatedTVShows()
    }

    private fun fetchTopRatedTVShows() {
        viewModelScope.launch {
            _topRatedTVShow.value = ResultStates.Loading
            repository.getTopRatedTVShows()
                .collect { result ->
                    _topRatedTVShow.value = result
                }
        }
    }

}