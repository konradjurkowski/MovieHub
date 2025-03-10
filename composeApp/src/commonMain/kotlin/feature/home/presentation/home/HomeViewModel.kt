package feature.home.presentation.home

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.architecture.transformIf
import core.tools.dispatcher.DispatchersProvider
import feature.auth.data.remote.AuthService
import feature.home.presentation.draw.DrawType
import feature.home.presentation.home.HomeIntent.MoviePressed
import feature.home.presentation.home.HomeIntent.OnDrawMoviePressed
import feature.home.presentation.home.HomeIntent.OnDrawSeriesPressed
import feature.home.presentation.home.HomeIntent.OnUserPressed
import feature.home.presentation.home.HomeIntent.SeriesPressed
import feature.home.presentation.home.HomeIntent.TryAgainPressed
import feature.home.presentation.home.HomeSideEffect.GoToDrawMedia
import feature.home.presentation.home.HomeSideEffect.GoToMovieDetails
import feature.home.presentation.home.HomeSideEffect.GoToProfileTab
import feature.home.presentation.home.HomeSideEffect.GoToSeriesDetails
import feature.home.presentation.home.HomeState.Idle
import feature.home.presentation.home.HomeState.Loading
import feature.home.presentation.home.HomeState.Success
import feature.home.presentation.home.HomeState.Error
import feature.movies.data.repository.MovieRepository
import feature.series.data.repository.SeriesRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job

class HomeViewModel(
    private val authService: AuthService,
    private val movieRepository: MovieRepository,
    private val seriesRepository: SeriesRepository,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<HomeIntent, HomeSideEffect, HomeState>() {

    private var listenUserJob: Job? = null
    private var loadDataJob: Job? = null

    override fun getDefaultState() = Idle

    override fun processIntent(intent: HomeIntent) {
        when (intent) {
            is MoviePressed -> sendSideEffect(GoToMovieDetails(intent.movie))
            is SeriesPressed -> sendSideEffect(GoToSeriesDetails(intent.series))
            TryAgainPressed -> loadInitialData()
            OnDrawMoviePressed -> sendSideEffect(GoToDrawMedia(DrawType.MOVIE))
            OnDrawSeriesPressed -> sendSideEffect(GoToDrawMedia(DrawType.SERIES))
            OnUserPressed -> sendSideEffect(GoToProfileTab)
        }
    }

    fun loadInitialData() {
        if (loadDataJob?.isActive == true) return
        if (_viewState.value.isIdle()) updateViewState { Loading }

        loadDataJob = screenModelScope.launch(dispatchersProvider.io) {
            val futureUser = async { authService.getAppUser(true) }

            val futureFirebaseMovies = async { movieRepository.getFirebaseMovies() }
            val futureLastUpdatedMovies = async { movieRepository.getLastUpdatedFirebaseMovies() }

            val futureFirebaseSeries = async { seriesRepository.getFirebaseSeries() }
            val futureLastUpdatedSeries = async { seriesRepository.getLastUpdatedFirebaseSeries() }

            val userResult = futureUser.await()
            val moviesResult = futureFirebaseMovies.await()
            val lastUpdatedMoviesResult = futureLastUpdatedMovies.await()
            val seriesResult = futureFirebaseSeries.await()
            val lastUpdatedSeriesResult = futureLastUpdatedSeries.await()

            when {
                lastUpdatedMoviesResult.isSuccess() && lastUpdatedSeriesResult.isSuccess() && userResult.isSuccess() -> {
                    val data = Success(
                        firebaseMovies = moviesResult.getSuccess() ?: emptyList(),
                        firebaseSeries = seriesResult.getSuccess() ?: emptyList(),
                        lastUpdatedMovies = lastUpdatedMoviesResult.getSuccess() ?: emptyList(),
                        lastUpdatedSeries = lastUpdatedSeriesResult.getSuccess() ?: emptyList(),
                        appUser = userResult.getSuccess(),
                    )
                    updateViewState { data }
                    initializeListeners()
                }

                else -> updateViewState { Error() }
            }
        }
    }

    private fun initializeListeners() {
        if (listenUserJob?.isActive == true) return

        listenUserJob = authService.appUser.onEach {
            _viewState.transformIf<Success> {
                copy(appUser = it)
            }
        }.launchIn(screenModelScope)
    }
}
