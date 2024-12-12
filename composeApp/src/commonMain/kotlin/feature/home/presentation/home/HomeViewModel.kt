package feature.home.presentation.home

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.tools.dispatcher.DispatchersProvider
import feature.auth.data.remote.AuthService
import feature.movies.data.repository.MovieRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeViewModel(
    private val authService: AuthService,
    private val movieRepository: MovieRepository,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<HomeIntent, HomeSideEffect, HomeState>() {

    init {
        loadInitialData()
    }

    override fun getDefaultState() = HomeState()

    override fun processIntent(intent: HomeIntent) {

    }

    private fun loadInitialData() {
        updateViewState { copy(isLoading = true) }

        screenModelScope.launch(dispatchersProvider.io) {
            val futureMovies = async { movieRepository.getLastUpdatedFirebaseMovies() }
            val futureUser = async { authService.getAppUser() }

            val moviesResult = futureMovies.await()
            val userResult = futureUser.await()

            when {
                moviesResult.isSuccess() && userResult.isSuccess() -> {
                    updateViewState {
                        copy(
                            isLoading = false,
                            lastUpdatedMovies = moviesResult.getSuccess() ?: emptyList(),
                            user = userResult.getSuccess()
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
