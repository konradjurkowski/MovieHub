package feature.home.presentation.home

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.architecture.transformIf
import core.tools.dispatcher.DispatchersProvider
import feature.auth.data.remote.AuthService
import feature.home.presentation.draw.DrawType
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

    init {
        loadInitialData()
    }

    override fun getDefaultState() = HomeState.Idle

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
        updateViewState { HomeState.Loading }

        screenModelScope.launch(dispatchersProvider.io) {
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
                    val data = HomeState.Success(
                        firebaseMovies = moviesResult.getSuccess() ?: emptyList(),
                        firebaseSeries = seriesResult.getSuccess() ?: emptyList(),
                        lastUpdatedMovies = lastUpdatedMoviesResult.getSuccess() ?: emptyList(),
                        lastUpdatedSeries = lastUpdatedSeriesResult.getSuccess() ?: emptyList(),
                        appUser = userResult.getSuccess(),
                    )
                    updateViewState { data }
                    initializeListeners()
                }

                else -> updateViewState { HomeState.Error() }
            }
        }
    }

    private fun initializeListeners() {
        if (listenUserJob?.isActive == true) return

        listenUserJob = authService.appUser.onEach {
            _viewState.transformIf<HomeState.Success> {
                copy(appUser = it)
            }
        }.launchIn(screenModelScope)
    }
}
