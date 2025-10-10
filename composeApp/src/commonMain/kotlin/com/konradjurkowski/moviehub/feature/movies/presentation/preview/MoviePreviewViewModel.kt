package com.konradjurkowski.moviehub.feature.movies.presentation.preview

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.konradjurkowski.moviehub.core.architecture.BaseViewModel
import com.konradjurkowski.moviehub.core.domain.model.ActionState
import com.konradjurkowski.moviehub.core.domain.model.Response
import com.konradjurkowski.moviehub.core.domain.model.media.url
import com.konradjurkowski.moviehub.core.navigation.AppNavigator
import com.konradjurkowski.moviehub.core.utils.extensions.transformIf
import com.konradjurkowski.moviehub.core.utils.coroutines.DispatchersProvider
import com.konradjurkowski.moviehub.feature.movies.data.api.dto.request.toCreateRequest
import com.konradjurkowski.moviehub.feature.movies.domain.model.MovieDetails
import com.konradjurkowski.moviehub.feature.movies.domain.repository.MovieRepository
import com.konradjurkowski.moviehub.feature.movies.domain.storage.MovieStorage
import com.konradjurkowski.moviehub.feature.movies.navigation.MoviesDestination.MoviePreviewRoute
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.ise.MoviePreviewEvent
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.ise.MoviePreviewEvent.ShowError
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.ise.MoviePreviewEvent.ShowSuccess
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.ise.MoviePreviewIntent
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.ise.MoviePreviewIntent.BackPressed
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.ise.MoviePreviewIntent.MovieAddPressed
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.ise.MoviePreviewIntent.Refresh
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.ise.MoviePreviewIntent.VideoPressed
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.ise.MoviePreviewState
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.ise.MoviePreviewState.Error
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.ise.MoviePreviewState.Idle
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.ise.MoviePreviewState.Loading
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.ise.MoviePreviewState.Success
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MoviePreviewViewModel(
    private val movieRepository: MovieRepository,
    private val movieStorage: MovieStorage,
    private val navigator: AppNavigator,
    private val dispatchersProvider: DispatchersProvider,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<MoviePreviewIntent, MoviePreviewState, MoviePreviewEvent>(initialState = Idle) {

    private var args = savedStateHandle.toRoute<MoviePreviewRoute>()

    private var addingJob: Job? = null
    private var listenerJob: Job? = null

    init {
        loadInitialData()
    }

    override fun processIntent(intent: MoviePreviewIntent) {
        when (intent) {
            BackPressed -> navigator.back()
            is MovieAddPressed -> addMovie(intent.movie)
            Refresh -> loadInitialData()
            is VideoPressed -> navigator.openUrl(intent.video.url)
        }
    }

    private fun loadInitialData() {
        updateState { Loading }
        viewModelScope.launch(dispatchersProvider.io) {
            when (val result = movieRepository.getMoviePreview(tmdbId = args.tmdbId)) {
                is Response.Success -> {
                    initializeListeners()
                    updateState { Success(result.data) }
                }

                is Response.Failure -> {
                    updateState { Error(result.error) }
                }
            }
        }
    }

    private fun addMovie(movie: MovieDetails) {
        if (addingJob?.isActive == true) return

        _viewState.transformIf<Success> { copy(addState = ActionState.Loading) }
        addingJob = viewModelScope.launch(dispatchersProvider.io) {
            val request = movie.toCreateRequest(groupId = 1)
            when (val result = movieRepository.addMovie(request)) {
                is Response.Success -> {
                    sendEvent(ShowSuccess)
                    movieStorage.saveTmdbId(result.data.tmdbId)
                    _viewState.transformIf<Success> { copy(addState = ActionState.Success) }
                }

                is Response.Failure -> {
                    sendEvent(ShowError(result.error))
                    _viewState.transformIf<Success> { copy(addState = ActionState.Failure) }
                }
            }
        }
    }

    private fun initializeListeners() {
        if (listenerJob?.isActive == true) return

        listenerJob = movieStorage.tmdbIdsFlow.onEach { ids ->
            _viewState.transformIf<Success> { copy(movieAdded = ids.contains(args.tmdbId)) }
        }.launchIn(viewModelScope)
    }
}
