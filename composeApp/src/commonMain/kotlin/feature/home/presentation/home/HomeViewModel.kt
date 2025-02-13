package feature.home.presentation.home

import cafe.adriel.voyager.core.model.screenModelScope
import com.mmk.kmpnotifier.notification.NotifierManager
import core.architecture.BaseViewModel
import core.tools.dispatcher.DispatchersProvider
import core.utils.constants.Constants
import feature.auth.data.remote.AuthService
import feature.movies.data.repository.MovieRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeViewModel(
    private val authService: AuthService,
    private val movieRepository: MovieRepository,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<HomeIntent, HomeSideEffect, HomeState>() {

    init {
        loadInitialData()
        initializeListeners()
        subscribeToTopic()
    }

    override fun getDefaultState() = HomeState()

    override fun processIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.MoviePressed -> sendSideEffect(HomeSideEffect.GoToMovieDetail(intent.movie))
            HomeIntent.OnUserPressed -> sendSideEffect(HomeSideEffect.GoToProfileTab)
        }
    }

    private fun loadInitialData() {
        updateViewState { copy(isLoading = true) }

        screenModelScope.launch(dispatchersProvider.io) {
            val futureUser = async { authService.getAppUser(true) }
            async { movieRepository.getTopRatedFirebaseMovies() }
            val futureLastUpdatedMovies = async { movieRepository.getLastUpdatedFirebaseMovies() }

            futureUser.await()
            val moviesResult = futureLastUpdatedMovies.await()

            when {
                moviesResult.isSuccess() -> {
                    updateViewState {
                        copy(
                            isLoading = false,
                            lastUpdatedMovies = moviesResult.getSuccess(),
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

    private fun subscribeToTopic() {
        screenModelScope.launch(dispatchersProvider.io) {
            NotifierManager.getPushNotifier().subscribeToTopic(Constants.PUSH_NEWS_TOPIC)
        }
    }
}
