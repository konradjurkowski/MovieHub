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

    override fun getDefaultState() = MovieDetailsState.Idle

    override fun processIntent(intent: MovieDetailsIntent) {
        when (intent) {
            MovieDetailsIntent.BackPressed -> sendSideEffect(MovieDetailsSideEffect.NavigateBack)
            MovieDetailsIntent.Refresh -> getMovieDetails()
            is MovieDetailsIntent.SetTab -> _viewState.transformIf<MovieDetailsState.Success> { copy(selectedTab = intent.tab) }
            is MovieDetailsIntent.AddCommentPressed -> sendSideEffect(MovieDetailsSideEffect.GoToAddComment(intent.firebaseRating))
            is MovieDetailsIntent.DeleteCommentPressed -> deleteComment(intent.firebaseRating)
            is MovieDetailsIntent.VideoPressed -> sendSideEffect(MovieDetailsSideEffect.OpenUrl(intent.video.getVideoUrl()))
        }
    }

    private fun getMovieDetails() {
        if (_viewState.value == MovieDetailsState.Idle) updateViewState { MovieDetailsState.Loading }
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
                val data = MovieDetailsState.Success(
                    movie = movieResult.getSuccess()!!,
                    firebaseMovie = firebaseMovieResult.getSuccess()!!,
                    castData = creditsResult.getSuccess()!!,
                    users = usersResult.getSuccess()!!,
                )
                updateViewState { data }
                return@launch
            }

            updateViewState { MovieDetailsState.Error() }
        }
    }

    private fun deleteComment(rating: FirebaseRating) {
        sendSideEffect(MovieDetailsSideEffect.ShowLoader)

        screenModelScope.launch(dispatchersProvider.io) {
            when (val result = movieRepository.deleteFirebaseRating(movieId, rating)) {
                is Response.Success -> {
                    sendSideEffect(MovieDetailsSideEffect.HideLoaderWithSuccess)
                    getMovieDetails()
                }
                is Response.Failure -> {
                    sendSideEffect(MovieDetailsSideEffect.HideLoaderWithError(result.error))
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
