package feature.movies.presentation.preview

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.tools.dispatcher.DispatchersProvider
import core.tools.event_bus.EventBus
import core.tools.event_bus.RefreshMovieList
import core.utils.Resource
import feature.movies.data.repository.MovieRepository
import feature.movies.data.storage.MovieRegistry
import feature.movies.domain.model.MovieDetails
import feature.movies.domain.model.toMovie
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MoviePreviewViewModel(
    private val movieId: Long,
    private val movieRepository: MovieRepository,
    private val movieRegistry: MovieRegistry,
    private val eventBus: EventBus,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<MoviePreviewIntent, MoviePreviewSideEffect, MoviePreviewState>() {

    init {
        initializeListeners()
        getMovieDetails()
    }

    override fun getDefaultState() = MoviePreviewState()

    override fun processIntent(intent: MoviePreviewIntent) {
        when (intent) {
            MoviePreviewIntent.BackPressed -> sendSideEffect(MoviePreviewSideEffect.NavigateBack)
            MoviePreviewIntent.Refresh -> getMovieDetails()
            is MoviePreviewIntent.MovieAddPressed -> addMovie(intent.movie)
        }
    }

    private fun getMovieDetails() {
        updateViewState { copy(isLoading = true) }
        screenModelScope.launch(dispatchersProvider.io) {
            val futureMovie = async { movieRepository.getMovieById(movieId) }
            val futureCredits = async { movieRepository.getCredits(movieId) }

            val movieResult = futureMovie.await()
            val creditsResult = futureCredits.await()

            val resultList = listOf(movieResult, creditsResult)

            if (resultList.all { it.isSuccess() }) {
                updateViewState {
                    copy(
                        movie = movieResult.getSuccess()!!,
                        castData = creditsResult.getSuccess()!!,
                        isLoading = false,
                    )
                }
                return@launch
            }

            updateViewState { copy(isLoading = false) }
        }
    }

    private fun addMovie(movie: MovieDetails) {
        sendSideEffect(MoviePreviewSideEffect.ShowLoader)
        screenModelScope.launch(dispatchersProvider.io) {
            when (val result = movieRepository.addFirebaseMovie(movie.toMovie())) {
                is Resource.Success -> {
                    movieRegistry.addMovie(movieId)
                    updateViewState { copy(isMovieAdded = true) }
                    sendSideEffect(MoviePreviewSideEffect.HideLoaderWithSuccess)
                    eventBus.invokeEvent(RefreshMovieList)
                }
                is Resource.Failure -> {
                    sendSideEffect(MoviePreviewSideEffect.HideLoaderWithError(result.error))
                }
                else -> {
                    // NO - OP
                }
            }
        }
    }

    private fun initializeListeners() {
        movieRegistry.movies.onEach { movieIds ->
            updateViewState { copy(isMovieAdded = movieIds.contains(movieId)) }
        }.launchIn(screenModelScope)
    }
}
