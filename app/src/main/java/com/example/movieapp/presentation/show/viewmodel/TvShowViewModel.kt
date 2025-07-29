package com.example.movieapp.presentation.show.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.data.remote.model.tvShow.ResultTVShow
import com.example.movieapp.data.remote.model.tvShow.details.TVShowCreditsResponse
import com.example.movieapp.domain.usecase.show.GetOnTheAirTVShowsUseCase
import com.example.movieapp.domain.usecase.show.GetPopularTVShowsUseCase
import com.example.movieapp.domain.usecase.show.GetSimilarTVShowsUseCase
import com.example.movieapp.domain.usecase.show.GetTVShowCreditsUseCase
import com.example.movieapp.domain.usecase.show.GetTopRatedTVShowsUseCase
import com.example.movieapp.domain.usecase.show.GetTrendingTVShowsUseCase
import com.example.movieapp.domain.usecase.show.GetTvShowDetailsUseCase
import com.example.movieapp.presentation.state.ResultStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel @Inject constructor(
    getPopularTVShowsUseCase: GetPopularTVShowsUseCase,
    private val getOnTheAirTVShowsUseCase: GetOnTheAirTVShowsUseCase,
    getTopRatedTVShowsUseCase: GetTopRatedTVShowsUseCase,
    getTrendingTVShowsUseCase: GetTrendingTVShowsUseCase,
    private val getSimilarTVShowsUseCase: GetSimilarTVShowsUseCase,
    private val getTvShowDetailsUseCase: GetTvShowDetailsUseCase,
    private val getMovieCreditsUseCase: GetTVShowCreditsUseCase
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

    private val _castState = MutableStateFlow<ResultStates<TVShowCreditsResponse>>(ResultStates.Loading)
    val castState: StateFlow<ResultStates<TVShowCreditsResponse>> = _castState

    private val _similarTVShowId = MutableStateFlow<Int?>(null)
    val similarTVShows: Flow<PagingData<ResultTVShow>> = _similarTVShowId
        .filterNotNull()
        .flatMapLatest { tvShowId ->
            getSimilarTVShowsUseCase(tvShowId)
        }
        .cachedIn(viewModelScope)

    fun onEvent(event: TvShowsUiEvent) {
        when(event) {

            is TvShowsUiEvent.LoadOnTheAirTVShows -> {
                fetchOnTheAirTVShows()
            }

            is TvShowsUiEvent.LoadTvShowDetails -> {
                fetchTvShowDetails(event.tvShowId)
            }

            is TvShowsUiEvent.LoadTVShowCredits -> {
                fetchTVShowCredits(event.tvShowId)
            }

            is TvShowsUiEvent.LoadSimilarTVShows -> {
                fetchSimilarTVShows(event.tvShowId)
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

    private fun fetchTVShowCredits(tvShowId: Int) {
        viewModelScope.launch {
            _castState.value = ResultStates.Loading
            _castState.value = getMovieCreditsUseCase(tvShowId)
        }
    }

    private fun fetchSimilarTVShows(tvShowId: Int) {
        _similarTVShowId.value = tvShowId
    }
}

sealed class TvShowsUiEvent {
    data object LoadPopularTvShows : TvShowsUiEvent()
    data object LoadTopRatedTVShows: TvShowsUiEvent()
    data object LoadOnTheAirTVShows: TvShowsUiEvent()
    data object LoadTrendingTVShows: TvShowsUiEvent()
    data class LoadSimilarTVShows(val tvShowId: Int): TvShowsUiEvent()
    data class LoadTvShowDetails(val tvShowId: Int): TvShowsUiEvent()
    data class LoadTVShowCredits(val tvShowId: Int): TvShowsUiEvent()
}