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
import feature.movies.presentation.preview.MoviePreviewIntent.BackPressed
import feature.movies.presentation.preview.MoviePreviewIntent.MovieAddPressed
import feature.movies.presentation.preview.MoviePreviewIntent.Refresh
import feature.movies.presentation.preview.MoviePreviewIntent.VideoPressed
import feature.movies.presentation.preview.MoviePreviewSideEffect.HideLoaderWithError
import feature.movies.presentation.preview.MoviePreviewSideEffect.HideLoaderWithSuccess
import feature.movies.presentation.preview.MoviePreviewSideEffect.NavigateBack
import feature.movies.presentation.preview.MoviePreviewSideEffect.OpenUrl
import feature.movies.presentation.preview.MoviePreviewSideEffect.ShowLoader
import feature.movies.presentation.preview.MoviePreviewState.Idle
import feature.movies.presentation.preview.MoviePreviewState.Loading
import feature.movies.presentation.preview.MoviePreviewState.Success
import feature.movies.presentation.preview.MoviePreviewState.Error
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

    override fun getDefaultState() = Idle

    override fun processIntent(intent: MoviePreviewIntent) {
        when (intent) {
            BackPressed -> sendSideEffect(NavigateBack)
            Refresh -> getMovieDetails()
            is MovieAddPressed -> addMovie(intent.movie)
            is VideoPressed -> sendSideEffect(OpenUrl(intent.video.getVideoUrl()))
        }
    }

    private fun getMovieDetails() {
        updateViewState { Loading }
        screenModelScope.launch(dispatchersProvider.io) {
            val futureMovie = async { movieRepository.getMovieById(movieId) }
            val futureCredits = async { movieRepository.getCredits(movieId) }

            val movieResult = futureMovie.await()
            val creditsResult = futureCredits.await()

            val resultList = listOf(movieResult, creditsResult)

            if (resultList.all { it.isSuccess() }) {
                val data = Success(
                    movie = movieResult.getSuccess()!!,
                    castData = creditsResult.getSuccess()!!,
                )
                updateViewState { data }
                initializeListeners()
                return@launch
            }

            updateViewState { Error() }
        }
    }

    private fun addMovie(movie: MovieDetails) {
        sendSideEffect(ShowLoader)
        screenModelScope.launch(dispatchersProvider.io) {
            when (val result = movieRepository.addFirebaseMovie(movie.toMovie())) {
                is Response.Success -> {
                    movieRegistry.addMovie(movieId)
                    _viewState.transformIf<Success> { copy(isMovieAdded = true) }
                    sendSideEffect(HideLoaderWithSuccess)
                }

                is Response.Failure -> {
                    sendSideEffect(HideLoaderWithError(result.error))
                }
            }
        }
    }

    private fun initializeListeners() {
        if (listenMoviesRegistryJob?.isActive == true) return

        listenMoviesRegistryJob = movieRegistry.movies.onEach { movieIds ->
            _viewState.transformIf<Success> { copy(isMovieAdded = movieIds.contains(movieId)) }
        }.launchIn(screenModelScope)
    }
}
