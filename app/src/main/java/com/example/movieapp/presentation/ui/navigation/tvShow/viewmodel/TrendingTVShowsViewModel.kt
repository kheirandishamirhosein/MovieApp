package com.example.movieapp.presentation.ui.navigation.tvShow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.remote.model.tvShow.trending.TrendingTVShowsResponse
import com.example.movieapp.data.repository.Repository
import com.example.movieapp.presentation.state.ResultStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingTVShowsViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {

    private val _trending =
        MutableStateFlow<ResultStates<TrendingTVShowsResponse>>(ResultStates.Loading)
    val trending: StateFlow<ResultStates<TrendingTVShowsResponse>> = _trending

    init {
        fetchTrendingTVShow()
    }

    private fun fetchTrendingTVShow() {
        viewModelScope.launch {
            _trending.value = ResultStates.Loading
            repository.getTrendingTVShows()
                .collect { result ->
                    _trending.value = result
                }
        }
    }

}