package com.example.movieapp.presentation.tv.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.remote.model.tvShow.ResultTVShow
import com.example.movieapp.data.repository.RepositoryImp
import com.example.movieapp.presentation.state.ResultStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsTVShowsViewModel @Inject constructor(
    val repositoryImp: RepositoryImp
) : ViewModel() {

    private val _detailsTVShow = MutableStateFlow<ResultStates<ResultTVShow>>(ResultStates.Loading)
    val detailsTVShow: StateFlow<ResultStates<ResultTVShow>> = _detailsTVShow

    fun fetchDetailTVShows(movieId: Int) {
        viewModelScope.launch {
            _detailsTVShow.value = ResultStates.Loading
            repositoryImp.getTVShowDetails(movieId)
                .collect { result ->
                    _detailsTVShow.value = result
                }
        }
    }
}