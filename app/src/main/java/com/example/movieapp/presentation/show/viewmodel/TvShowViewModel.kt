package com.example.movieapp.presentation.show.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.data.remote.model.tvShow.ResultTVShow
import com.example.movieapp.domain.usecase.show.GetOnTheAirTVShowsUseCase
import com.example.movieapp.domain.usecase.show.GetPopularTVShowsUseCase
import com.example.movieapp.domain.usecase.show.GetTopRatedTVShowsUseCase
import com.example.movieapp.domain.usecase.show.GetTrendingTVShowsUseCase
import com.example.movieapp.domain.usecase.show.GetTvShowDetailsUseCase
import com.example.movieapp.presentation.state.ResultStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel @Inject constructor(
    getPopularTVShowsUseCase: GetPopularTVShowsUseCase,
    private val getOnTheAirTVShowsUseCase: GetOnTheAirTVShowsUseCase,
    getTopRatedTVShowsUseCase: GetTopRatedTVShowsUseCase,
    getTrendingTVShowsUseCase: GetTrendingTVShowsUseCase,
    private val getTvShowDetailsUseCase: GetTvShowDetailsUseCase
): ViewModel() {

    val popularTVShows: Flow<PagingData<ResultTVShow>> =
        getPopularTVShowsUseCase()
            .cachedIn(viewModelScope)

    val topRatedTVShowsPaging: Flow<PagingData<ResultTVShow>> =
        getTopRatedTVShowsUseCase().cachedIn(viewModelScope)

    private val _onTheAirTVShows = MutableStateFlow<ResultStates<List<ResultTVShow>>>(ResultStates.Loading)
    val onTheAirTVShows: StateFlow<ResultStates<List<ResultTVShow>>> = _onTheAirTVShows

    val trendingTVShowsPaging: Flow<PagingData<ResultTVShow>> =
        getTrendingTVShowsUseCase().cachedIn(viewModelScope)

    private val _tvShowDetailsState = MutableStateFlow<ResultStates<ResultTVShow>>(ResultStates.Loading)
    val tvShowDetailsState: StateFlow<ResultStates<ResultTVShow>> = _tvShowDetailsState

    fun onEvent(event: TvShowsUiEvent) {
        when(event) {

            is TvShowsUiEvent.LoadOnTheAirTVShows -> {
                fetchOnTheAirTVShows()
            }

            is TvShowsUiEvent.LoadTvShowDetails -> {
                fetchTvShowDetails(event.tvShowId)
            }
            else -> Unit
        }
    }

    private fun fetchOnTheAirTVShows() {
        viewModelScope.launch {
            _onTheAirTVShows.value = ResultStates.Loading
            _onTheAirTVShows.value = getOnTheAirTVShowsUseCase()
        }
    }

    private fun fetchTvShowDetails(tvShowId: Int) {
        viewModelScope.launch {
            _tvShowDetailsState.value = ResultStates.Loading
            _tvShowDetailsState.value = getTvShowDetailsUseCase(tvShowId)
        }
    }
}

sealed class TvShowsUiEvent {
    data object LoadPopularTvShows : TvShowsUiEvent()
    data object LoadTopRatedTVShows: TvShowsUiEvent()
    data object LoadOnTheAirTVShows: TvShowsUiEvent()
    data object LoadTrendingTVShows: TvShowsUiEvent()
    data class LoadTvShowDetails(val tvShowId: Int): TvShowsUiEvent()
}