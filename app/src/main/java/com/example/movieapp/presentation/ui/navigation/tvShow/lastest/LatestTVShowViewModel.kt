package com.example.movieapp.presentation.ui.navigation.tvShow.lastest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.remote.model.tvShow.TVShowDetails
import com.example.movieapp.data.repository.Repository
import com.example.movieapp.presentation.state.ResultStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LatestTVShowViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _latestTVShow = MutableStateFlow<ResultStates<TVShowDetails>>(ResultStates.Loading)
    val latestTVShow: StateFlow<ResultStates<TVShowDetails>> = _latestTVShow

    init {
        fetchLatestTVShows()
    }

    private fun fetchLatestTVShows() {
        viewModelScope.launch {
            repository.getLatestTVShows()
                .collect { result ->
                    _latestTVShow.value = result
                }
        }
    }
}