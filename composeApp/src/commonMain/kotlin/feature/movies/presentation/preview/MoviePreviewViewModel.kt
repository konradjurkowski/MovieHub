package feature.movies.presentation.preview

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.architecture.transformIf
import core.model.Response
import core.model.media.getVideoUrl
import core.tools.dispatcher.DispatchersProvider
import feature.movies.data.repository.MovieRepository
import feature.movies.data.storage.MovieRegistry
import feature.movies.domain.model.MovieDetails
import feature.movies.domain.model.toMovie
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job

class MoviePreviewViewModel(
    private val movieId: Long,
    private val movieRepository: MovieRepository,
    private val movieRegistry: MovieRegistry,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<MoviePreviewIntent, MoviePreviewSideEffect, MoviePreviewState>() {

    private var listenMoviesRegistryJob : Job? = null

    init {
        getMovieDetails()
    }

    override fun getDefaultState() = MoviePreviewState.Idle

    override fun processIntent(intent: MoviePreviewIntent) {
        when (intent) {
            MoviePreviewIntent.BackPressed -> sendSideEffect(MoviePreviewSideEffect.NavigateBack)
            MoviePreviewIntent.Refresh -> getMovieDetails()
            is MoviePreviewIntent.MovieAddPressed -> addMovie(intent.movie)
            is MoviePreviewIntent.VideoPressed -> sendSideEffect(MoviePreviewSideEffect.OpenUrl(intent.video.getVideoUrl()))
        }
    }

    private fun getMovieDetails() {
        updateViewState { MoviePreviewState.Loading }
        screenModelScope.launch(dispatchersProvider.io) {
            val futureMovie = async { movieRepository.getMovieById(movieId) }
            val futureCredits = async { movieRepository.getCredits(movieId) }

            val movieResult = futureMovie.await()
            val creditsResult = futureCredits.await()

            val resultList = listOf(movieResult, creditsResult)

            if (resultList.all { it.isSuccess() }) {
                val data = MoviePreviewState.Success(
                    movie = movieResult.getSuccess()!!,
                    castData = creditsResult.getSuccess()!!,
                )
                updateViewState { data }
                initializeListeners()
                return@launch
            }

            updateViewState { MoviePreviewState.Error() }
        }
    }

    private fun addMovie(movie: MovieDetails) {
        sendSideEffect(MoviePreviewSideEffect.ShowLoader)
        screenModelScope.launch(dispatchersProvider.io) {
            when (val result = movieRepository.addFirebaseMovie(movie.toMovie())) {
                is Response.Success -> {
                    movieRegistry.addMovie(movieId)
                    _viewState.transformIf<MoviePreviewState.Success> { copy(isMovieAdded = true) }
                    sendSideEffect(MoviePreviewSideEffect.HideLoaderWithSuccess)
                }

                is Response.Failure -> {
                    sendSideEffect(MoviePreviewSideEffect.HideLoaderWithError(result.error))
                }
            }
        }
    }

    private fun initializeListeners() {
        if (listenMoviesRegistryJob?.isActive == true) return

        listenMoviesRegistryJob = movieRegistry.movies.onEach { movieIds ->
            _viewState.transformIf<MoviePreviewState.Success> { copy(isMovieAdded = movieIds.contains(movieId)) }
        }.launchIn(screenModelScope)
    }
}
