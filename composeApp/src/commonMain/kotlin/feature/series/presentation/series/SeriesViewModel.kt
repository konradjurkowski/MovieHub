package feature.series.presentation.series

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import core.utils.FailureResponseException
import core.utils.Resource
import feature.movies.data.api.dto.Genre
import feature.series.data.repository.SeriesRepository
import feature.series.domain.model.SeriesOverview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

typealias SeriesState = Resource<SeriesOverview>

class SeriesViewModel(
    private val repository: SeriesRepository
) : ScreenModel {
    private val _state = MutableStateFlow<SeriesState>(Resource.Idle)
    val state: StateFlow<SeriesState> = _state.asStateFlow()

    init {
        fetchSeries()
    }

    fun fetchSeries() {
        if (_state.value.isLoading()) return

        _state.value = Resource.Loading
        screenModelScope.launch(Dispatchers.IO) {
            val popularFuture = async { repository.getPopularSeries() }
            val topRatedFuture = async { repository.getTopRatedSeries() }
            val genresFuture = async {
                when (localSeriesGenresList.isEmpty()) {
                    true -> repository.getGenres()
                    false -> Resource.Success(localSeriesGenresList)
                }
            }

            val popularResult = popularFuture.await()
            val topRatedResult = topRatedFuture.await()
            val genresResult = genresFuture.await()

            if (popularResult.isSuccess() && topRatedResult.isSuccess() && genresResult.isSuccess()) {
                val popularSeries = popularResult.getSuccess() ?: emptyList()
                val topRatedSeries = topRatedResult.getSuccess() ?: emptyList()
                val genresList = genresResult.getSuccess() ?: emptyList()
                localSeriesGenresList = genresList

                val seriesOverview = SeriesOverview(popularSeries, topRatedSeries)
                _state.value = Resource.Success(seriesOverview)
            } else {
                _state.value = Resource.Failure(FailureResponseException())
            }
        }
    }
}

var localSeriesGenresList: List<Genre> = emptyList()
