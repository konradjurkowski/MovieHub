package feature.home.presentation.home

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.tools.dispatcher.DispatchersProvider
import feature.auth.data.remote.AuthService
import feature.home.presentation.draw.DrawType
import feature.movies.data.repository.MovieRepository
import feature.series.data.repository.SeriesRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeViewModel(
    private val authService: AuthService,
    private val movieRepository: MovieRepository,
    private val seriesRepository: SeriesRepository,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<HomeIntent, HomeSideEffect, HomeState>() {

    init {
        loadInitialData()
        initializeListeners()
    }

    override fun getDefaultState() = HomeState()

    override fun processIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.MoviePressed -> sendSideEffect(HomeSideEffect.GoToMovieDetails(intent.movie))
            is HomeIntent.SeriesPressed -> sendSideEffect(HomeSideEffect.GoToSeriesDetails(intent.series))
            HomeIntent.TryAgainPressed -> loadInitialData()
            HomeIntent.OnDrawMoviePressed -> sendSideEffect(HomeSideEffect.GoToDrawMedia(DrawType.MOVIE))
            HomeIntent.OnDrawSeriesPressed -> sendSideEffect(HomeSideEffect.GoToDrawMedia(DrawType.SERIES))
            HomeIntent.OnUserPressed -> sendSideEffect(HomeSideEffect.GoToProfileTab)
        }
    }

    private fun loadInitialData() {
        updateViewState { copy(isLoading = true) }

        screenModelScope.launch(dispatchersProvider.io) {
            val futureUser = async { authService.getAppUser(true) }

            val futureFirebaseMovies = async { movieRepository.getFirebaseMovies() }
            val futureLastUpdatedMovies = async { movieRepository.getLastUpdatedFirebaseMovies() }

            val futureFirebaseSeries = async { seriesRepository.getFirebaseSeries() }
            val futureLastUpdatedSeries = async { seriesRepository.getLastUpdatedFirebaseSeries() }

            futureUser.await()
            val moviesResult = futureFirebaseMovies.await()
            val lastUpdatedMoviesResult = futureLastUpdatedMovies.await()
            val seriesResult = futureFirebaseSeries.await()
            val lastUpdatedSeriesResult = futureLastUpdatedSeries.await()

            when {
                lastUpdatedMoviesResult.isSuccess() && lastUpdatedSeriesResult.isSuccess() -> {
                    updateViewState {
                        copy(
                            isLoading = false,
                            firebaseMovies = moviesResult.getSuccess() ?: emptyList(),
                            lastUpdatedMovies = lastUpdatedMoviesResult.getSuccess(),
                            firebaseSeries = seriesResult.getSuccess() ?: emptyList(),
                            lastUpdatedSeries = lastUpdatedSeriesResult.getSuccess(),
                        )
                    }
                }
                else -> {
                    updateViewState { copy(isLoading = false) }
                }
            }
        }
    }

    private fun initializeListeners() {
        authService.appUser.onEach {
            updateViewState { copy(appUser = it) }
        }.launchIn(screenModelScope)
    }
}
