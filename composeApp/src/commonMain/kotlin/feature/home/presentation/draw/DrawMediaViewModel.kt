package feature.home.presentation.draw

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.architecture.invokeIf
import core.architecture.transformIf
import core.tools.dispatcher.DispatchersProvider
import core.tools.shake.ShakeDetector
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

    override fun getDefaultState() = DrawMediaState.Idle

    override fun processIntent(intent: DrawMediaIntent) {
        when (intent) {
            DrawMediaIntent.TryAgainPressed -> loadInitialData()
            is DrawMediaIntent.MoviePressed -> sendSideEffect(DrawMediaSideEffect.GoToMovieDetails(intent.movie))
            is DrawMediaIntent.SeriesPressed -> sendSideEffect(DrawMediaSideEffect.GoToSeriesDetails(intent.series))
            DrawMediaIntent.OnShakePressed -> handleShake()
        }
    }

    private fun handleShake() {
        _viewState.invokeIf<DrawMediaState.Success> {
            val counter = shakeCount + 1
            if (counter > 3) return@invokeIf

            shakeDetector.manualShake()
            _viewState.transformIf<DrawMediaState.Success> { copy(shakeCount = counter) }

            if (counter >= 3) {
                shakeDetector.stop()
                when (drawType) {
                    DrawType.MOVIE -> _viewState.transformIf<DrawMediaState.Success> { copy(selectedMovie = firebaseMovies.randomOrNull()) }
                    DrawType.SERIES -> _viewState.transformIf<DrawMediaState.Success> { copy(selectedSeries = firebaseSeries.randomOrNull()) }
                }
            }
        }
    }

    private fun loadInitialData() {
        updateViewState { DrawMediaState.Loading }

        screenModelScope.launch(dispatchersProvider.io) {
            val futureFirebaseMovies = async { movieRepository.getFirebaseMovies() }
            val futureFirebaseSeries = async { seriesRepository.getFirebaseSeries() }

            val moviesResult = futureFirebaseMovies.await()
            val seriesResult = futureFirebaseSeries.await()

            when {
                moviesResult.isSuccess() && seriesResult.isSuccess() -> {
                    shakeDetector.start { handleShake() }
                    val data = DrawMediaState.Success(
                        firebaseMovies = moviesResult.getSuccess() ?: emptyList(),
                        firebaseSeries = seriesResult.getSuccess() ?: emptyList(),
                    )
                    updateViewState { data }
                }

                else -> updateViewState { DrawMediaState.Error() }
            }
        }
    }
}
