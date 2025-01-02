package feature.movies.presentation.details

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.tools.dispatcher.DispatchersProvider
import core.tools.event_bus.EventBus
import core.tools.event_bus.RefreshMovie
import core.utils.Resource
import feature.auth.data.remote.AuthService
import feature.movies.data.repository.MovieRepository
import feature.movies.domain.model.FirebaseRating
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val movieId: Long,
    private val movieRepository: MovieRepository,
    private val authService: AuthService,
    private val eventBus: EventBus,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<MovieDetailsIntent, MovieDetailsSideEffect, MovieDetailsState>() {

    init {
        initListener()
        getMovieDetails()
    }

    override fun getDefaultState() = MovieDetailsState()

    override fun processIntent(intent: MovieDetailsIntent) {
        when (intent) {
            MovieDetailsIntent.BackPressed -> sendSideEffect(MovieDetailsSideEffect.NavigateBack)
            MovieDetailsIntent.Refresh -> getMovieDetails()
            is MovieDetailsIntent.SetTab -> updateViewState { copy(selectedTab = intent.tab) }
            is MovieDetailsIntent.AddCommentPressed -> sendSideEffect(MovieDetailsSideEffect.GoToAddComment(intent.firebaseRating))
            is MovieDetailsIntent.DeleteCommentPressed -> deleteComment(intent.firebaseRating)
        }
    }

    private fun getMovieDetails() {
        if (!isDataLoaded()) updateViewState { copy(isLoading = true) }
        screenModelScope.launch(dispatchersProvider.io) {
            val futureMovie = async { movieRepository.getMovieById(movieId) }
            val futureFirebaseMovie = async { movieRepository.getFirebaseMovieById(movieId) }
            val futureCredits = async { movieRepository.getCredits(movieId) }
            val futureUsers = async { authService.getAllAppUsers() }

            val movieResult = futureMovie.await()
            val firebaseMovieResult = futureFirebaseMovie.await()
            val creditsResult = futureCredits.await()
            val usersResult = futureUsers.await()

            val resultList = listOf(movieResult, firebaseMovieResult, creditsResult, usersResult)

            if (resultList.all { it.isSuccess() }) {
                updateViewState {
                    copy(
                        movie = movieResult.getSuccess()!!,
                        firebaseMovie = firebaseMovieResult.getSuccess()!!,
                        castData = creditsResult.getSuccess()!!,
                        users = usersResult.getSuccess()!!,
                        isLoading = false,
                    )
                }
                return@launch
            }

            updateViewState { copy(isLoading = false) }
        }
    }

    private fun deleteComment(rating: FirebaseRating) {
        sendSideEffect(MovieDetailsSideEffect.ShowLoader)

        screenModelScope.launch(dispatchersProvider.io) {
            when (val result = movieRepository.deleteFirebaseRating(movieId, rating)) {
                is Resource.Success -> {
                    sendSideEffect(MovieDetailsSideEffect.HideLoaderWithSuccess)
                    getMovieDetails()
                }
                is Resource.Failure -> {
                    sendSideEffect(MovieDetailsSideEffect.HideLoaderWithError(result.error))
                }
                else -> {
                    // NO - OP
                }
            }
        }
    }

    private fun initListener() {
        screenModelScope.launch {
            eventBus.events
                .filterIsInstance<RefreshMovie>()
                .filter { movieId == it.movieId }
                .collectLatest {
                    getMovieDetails()
                }
        }
    }

    private fun isDataLoaded(): Boolean {
        val state = viewState.value
        return state.movie != null &&
                state.firebaseMovie != null &&
                state.castData != null &&
                state.users.isNotEmpty()
    }
}
