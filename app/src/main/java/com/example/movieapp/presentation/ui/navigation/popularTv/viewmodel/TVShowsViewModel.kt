package com.example.movieapp.presentation.ui.navigation.popularTv.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.remote.model.tvShow.TVShowResponse
import com.example.movieapp.data.repository.Repository
import com.example.movieapp.presentation.state.ResultStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TVShowsViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {

    private val _tvShow = MutableStateFlow<ResultStates<TVShowResponse>>(ResultStates.Loading)
    val tvShow: StateFlow<ResultStates<TVShowResponse>> = _tvShow

    init {
        fetchPopularTVShow()
    }

    private fun fetchPopularTVShow() {
        viewModelScope.launch {
            repository.getPopularTVShows()
                .collect { result ->
                    _tvShow.value = result
                }
        }
    }

}