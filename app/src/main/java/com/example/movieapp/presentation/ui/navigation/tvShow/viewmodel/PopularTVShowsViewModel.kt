package com.example.movieapp.presentation.ui.navigation.tvShow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.remote.model.tvShow.popular.PopularTVShowResponse
import com.example.movieapp.data.repository.Repository
import com.example.movieapp.presentation.state.ResultStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularTVShowsViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {

    private val _tvShow =
        MutableStateFlow<ResultStates<PopularTVShowResponse>>(ResultStates.Loading)
    val tvShow: StateFlow<ResultStates<PopularTVShowResponse>> = _tvShow

    init {
        fetchPopularTvShow()
    }

    private fun fetchPopularTvShow() {
        viewModelScope.launch {
            _tvShow.value = ResultStates.Loading
            repository.getPopularTVShows()
                .collect { result ->
                    _tvShow.value = result
                }
        }
    }
}