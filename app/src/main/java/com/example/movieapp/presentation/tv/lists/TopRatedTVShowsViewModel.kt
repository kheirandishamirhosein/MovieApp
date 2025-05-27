package com.example.movieapp.presentation.tv.lists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.remote.model.tvShow.topRated.TopRatedTVShowsResponse
import com.example.movieapp.data.repository.RepositoryImp
import com.example.movieapp.presentation.state.ResultStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopRatedTVShowsViewModel @Inject constructor(
    val repositoryImp: RepositoryImp
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
            repositoryImp.getTopRatedTVShows()
                .collect { result ->
                    _topRatedTVShow.value = result
                }
        }
    }

}