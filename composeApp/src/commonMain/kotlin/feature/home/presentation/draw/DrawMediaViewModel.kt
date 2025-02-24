package feature.home.presentation.draw

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
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

    override fun getDefaultState() = DrawMediaState()

    override fun processIntent(intent: DrawMediaIntent) {
        when (intent) {
            DrawMediaIntent.OnShakePressed -> {
                shakeDetector.manualShake()
                handleShake()
            }

            DrawMediaIntent.TryAgainPressed -> loadInitialData()

            is DrawMediaIntent.MoviePressed -> sendSideEffect(DrawMediaSideEffect.GoToMovieDetails(intent.movie))

            is DrawMediaIntent.SeriesPressed -> sendSideEffect(DrawMediaSideEffect.GoToSeriesDetails(intent.series))
        }
    }

    private fun initializeShakeDetector() {
        shakeDetector.start { handleShake() }
    }

    private fun handleShake() {
        val shakeCount = viewState.value.shakeCount
        updateViewState { copy(shakeCount = shakeCount + 1) }

        if (viewState.value.shakeCount >= 3) {
            when (drawType) {
                DrawType.MOVIE -> {
                    updateViewState { copy(selectedMovie = firebaseMovies?.randomOrNull()) }
                }

                DrawType.SERIES -> {
                    updateViewState { copy(selectedSeries = firebaseSeries?.randomOrNull()) }
                }
            }
            shakeDetector.stop()
        }
    }

    private fun loadInitialData() {
        updateViewState { copy(isLoading = true) }

        screenModelScope.launch(dispatchersProvider.io) {
            val futureFirebaseMovies = async { movieRepository.getFirebaseMovies() }
            val futureFirebaseSeries = async { seriesRepository.getFirebaseSeries() }

            val moviesResult = futureFirebaseMovies.await()
            val seriesResult = futureFirebaseSeries.await()

            when {
                moviesResult.isSuccess() && seriesResult.isSuccess() -> {
                    initializeShakeDetector()
                    updateViewState {
                        copy(
                            isLoading = false,
                            firebaseMovies = moviesResult.getSuccess() ?: emptyList(),
                            firebaseSeries = seriesResult.getSuccess() ?: emptyList(),
                        )
                    }
                }
                else -> {
                    updateViewState { copy(isLoading = false) }
                }
            }
        }
    }
}
