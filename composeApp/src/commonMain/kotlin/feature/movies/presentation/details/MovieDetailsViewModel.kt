package feature.movies.presentation.details

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.architecture.transformIf
import core.model.Response
import core.model.media.getVideoUrl
import core.tools.dispatcher.DispatchersProvider
import core.tools.event_bus.EventBus
import core.tools.event_bus.RefreshMovie
import feature.auth.data.remote.AuthService
import feature.movies.data.repository.MovieRepository
import feature.movies.domain.model.FirebaseRating
import feature.movies.presentation.details.MovieDetailsIntent.AddCommentPressed
import feature.movies.presentation.details.MovieDetailsIntent.BackPressed
import feature.movies.presentation.details.MovieDetailsIntent.DeleteCommentPressed
import feature.movies.presentation.details.MovieDetailsIntent.Refresh
import feature.movies.presentation.details.MovieDetailsIntent.SetTab
import feature.movies.presentation.details.MovieDetailsIntent.VideoPressed
import feature.movies.presentation.details.MovieDetailsSideEffect.GoToAddComment
import feature.movies.presentation.details.MovieDetailsSideEffect.HideLoaderWithError
import feature.movies.presentation.details.MovieDetailsSideEffect.HideLoaderWithSuccess
import feature.movies.presentation.details.MovieDetailsSideEffect.NavigateBack
import feature.movies.presentation.details.MovieDetailsSideEffect.OpenUrl
import feature.movies.presentation.details.MovieDetailsSideEffect.ShowLoader
import feature.movies.presentation.details.MovieDetailsState.Idle
import feature.movies.presentation.details.MovieDetailsState.Loading
import feature.movies.presentation.details.MovieDetailsState.Success
import feature.movies.presentation.details.MovieDetailsState.Error
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

    override fun getDefaultState() = Idle

    override fun processIntent(intent: MovieDetailsIntent) {
        when (intent) {
            BackPressed -> sendSideEffect(NavigateBack)
            Refresh -> getMovieDetails()
            is SetTab -> _viewState.transformIf<Success> { copy(selectedTab = intent.tab) }
            is AddCommentPressed -> sendSideEffect(GoToAddComment(intent.firebaseRating))
            is DeleteCommentPressed -> deleteComment(intent.firebaseRating)
            is VideoPressed -> sendSideEffect(OpenUrl(intent.video.getVideoUrl()))
        }
    }

    private fun getMovieDetails() {
        if (_viewState.value == Idle) updateViewState { Loading }
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
                val data = Success(
                    movie = movieResult.getSuccess()!!,
                    firebaseMovie = firebaseMovieResult.getSuccess()!!,
                    castData = creditsResult.getSuccess()!!,
                    users = usersResult.getSuccess()!!,
                )
                updateViewState { data }
                return@launch
            }

            updateViewState { Error() }
        }
    }

    private fun deleteComment(rating: FirebaseRating) {
        sendSideEffect(ShowLoader)

        screenModelScope.launch(dispatchersProvider.io) {
            when (val result = movieRepository.deleteFirebaseRating(movieId, rating)) {
                is Response.Success -> {
                    sendSideEffect(HideLoaderWithSuccess)
                    getMovieDetails()
                }
                is Response.Failure -> {
                    sendSideEffect(HideLoaderWithError(result.error))
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
}
