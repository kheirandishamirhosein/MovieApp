package com.example.movieapp.presentation.tv.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.remote.model.tvShow.ResultTVShow
import com.example.movieapp.domain.usecase.show.GetOnTheAirTVShowsUseCase
import com.example.movieapp.domain.usecase.show.GetPopularTVShowsUseCase
import com.example.movieapp.domain.usecase.show.GetTopRatedTVShowsUseCase
import com.example.movieapp.domain.usecase.show.GetTrendingTVShowsUseCase
import com.example.movieapp.domain.usecase.show.GetTvShowDetailsUseCase
import com.example.movieapp.presentation.state.ResultStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel @Inject constructor(
    private val getPopularTVShowsUseCase: GetPopularTVShowsUseCase,
    private val getOnTheAirTVShowsUseCase: GetOnTheAirTVShowsUseCase,
    private val getTopRatedTVShowsUseCase: GetTopRatedTVShowsUseCase,
    private val getTrendingTVShowsUseCase: GetTrendingTVShowsUseCase,
    private val getTvShowDetailsUseCase: GetTvShowDetailsUseCase
): ViewModel() {

    private val _popularTVShows = MutableStateFlow<ResultStates<List<ResultTVShow>>>(ResultStates.Loading)
    val popularTVShows: StateFlow<ResultStates<List<ResultTVShow>>> = _popularTVShows

    private val _topRatedTVShows = MutableStateFlow<ResultStates<List<ResultTVShow>>>(ResultStates.Loading)
    val topRatedTVShows: StateFlow<ResultStates<List<ResultTVShow>>> = _topRatedTVShows

    private val _onTheAirTVShows = MutableStateFlow<ResultStates<List<ResultTVShow>>>(ResultStates.Loading)
    val onTheAirTVShows: StateFlow<ResultStates<List<ResultTVShow>>> = _onTheAirTVShows

    private val _trendingTVShows = MutableStateFlow<ResultStates<List<ResultTVShow>>>(ResultStates.Loading)
    val trendingTVShows: StateFlow<ResultStates<List<ResultTVShow>>> = _trendingTVShows

    private val _tvShowDetailsState = MutableStateFlow<ResultStates<ResultTVShow>>(ResultStates.Loading)
    val tvShowDetailsState: StateFlow<ResultStates<ResultTVShow>> = _tvShowDetailsState

    fun onEvent(event: TvShowsUiEvent) {
        when(event) {
            is TvShowsUiEvent.LoadPopularTvShows -> {
                fetchPopularTVShows()
            }

            is TvShowsUiEvent.LoadTopRatedTVShows -> {
                fetchTopRatedTVShows()
            }

            is TvShowsUiEvent.LoadTrendingTVShows -> {
                fetchTrendingTVShows()
            }

            is TvShowsUiEvent.LoadOnTheAirTVShows -> {
                fetchOnTheAirTVShows()
            }

            is TvShowsUiEvent.LoadTvShowDetails -> {
                fetchTvShowDetails(event.tvShowId)
            }
        }
    }

    private fun fetchPopularTVShows() {
        viewModelScope.launch {
            _popularTVShows.value = ResultStates.Loading
            _popularTVShows.value = getPopularTVShowsUseCase()
        }
    }

    private fun fetchTopRatedTVShows() {
        viewModelScope.launch {
            _topRatedTVShows.value = ResultStates.Loading
            _topRatedTVShows.value = getTopRatedTVShowsUseCase()
        }
    }

    private fun fetchOnTheAirTVShows() {
        viewModelScope.launch {
            _onTheAirTVShows.value = ResultStates.Loading
            _onTheAirTVShows.value = getOnTheAirTVShowsUseCase()
        }
    }

    private fun fetchTrendingTVShows() {
        viewModelScope.launch {
            _trendingTVShows.value = ResultStates.Loading
            _trendingTVShows.value = getTrendingTVShowsUseCase()
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