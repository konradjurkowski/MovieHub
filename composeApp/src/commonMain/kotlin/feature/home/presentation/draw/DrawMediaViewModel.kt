package feature.home.presentation.draw

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.architecture.invokeIf
import core.architecture.transformIf
import core.tools.dispatcher.DispatchersProvider
import core.tools.shake.ShakeDetector
import feature.home.presentation.draw.DrawMediaIntent.MoviePressed
import feature.home.presentation.draw.DrawMediaIntent.OnShakePressed
import feature.home.presentation.draw.DrawMediaIntent.SeriesPressed
import feature.home.presentation.draw.DrawMediaIntent.TryAgainPressed
import feature.home.presentation.draw.DrawMediaSideEffect.GoToMovieDetails
import feature.home.presentation.draw.DrawMediaSideEffect.GoToSeriesDetails
import feature.home.presentation.draw.DrawMediaState.Idle
import feature.home.presentation.draw.DrawMediaState.Loading
import feature.home.presentation.draw.DrawMediaState.Success
import feature.home.presentation.draw.DrawMediaState.Error
import feature.movies.data.repository.MovieRepository
import feature.series.data.repository.SeriesRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DrawMediaViewModel(
    private val drawType: DrawType,
    private val movieRepository: MovieRepository,
    private val seriesRepository: SeriesRepository,
    private val shakeDetector: ShakeDetector,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<DrawMediaIntent, DrawMediaSideEffect, DrawMediaState>() {

    init {
        loadInitialData()
    }

    override fun getDefaultState() = Idle

    override fun processIntent(intent: DrawMediaIntent) {
        when (intent) {
            TryAgainPressed -> loadInitialData()
            is MoviePressed -> sendSideEffect(GoToMovieDetails(intent.movie))
            is SeriesPressed -> sendSideEffect(GoToSeriesDetails(intent.series))
            OnShakePressed -> handleShake()
        }
    }

    private fun handleShake() {
        _viewState.invokeIf<Success> {
            val counter = shakeCount + 1
            if (counter > 3) return@invokeIf

            shakeDetector.manualShake()
            _viewState.transformIf<Success> { copy(shakeCount = counter) }

            if (counter >= 3) {
                shakeDetector.stop()
                when (drawType) {
                    DrawType.MOVIE -> _viewState.transformIf<Success> { copy(selectedMovie = firebaseMovies.randomOrNull()) }
                    DrawType.SERIES -> _viewState.transformIf<Success> { copy(selectedSeries = firebaseSeries.randomOrNull()) }
                }
            }
        }
    }

    private fun loadInitialData() {
        updateViewState { Loading }

        screenModelScope.launch(dispatchersProvider.io) {
            val futureFirebaseMovies = async { movieRepository.getFirebaseMovies() }
            val futureFirebaseSeries = async { seriesRepository.getFirebaseSeries() }

            val moviesResult = futureFirebaseMovies.await()
            val seriesResult = futureFirebaseSeries.await()

            when {
                moviesResult.isSuccess() && seriesResult.isSuccess() -> {
                    shakeDetector.start { handleShake() }
                    val data = Success(
                        firebaseMovies = moviesResult.getSuccess() ?: emptyList(),
                        firebaseSeries = seriesResult.getSuccess() ?: emptyList(),
                    )
                    updateViewState { data }
                }

                else -> updateViewState { Error() }
            }
        }
    }
}
