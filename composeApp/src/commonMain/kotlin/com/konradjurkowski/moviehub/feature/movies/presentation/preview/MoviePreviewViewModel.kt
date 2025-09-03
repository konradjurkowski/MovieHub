package com.konradjurkowski.moviehub.feature.movies.presentation.preview

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.konradjurkowski.moviehub.core.architecture.BaseViewModel
import com.konradjurkowski.moviehub.core.domain.model.Response
import com.konradjurkowski.moviehub.core.domain.model.media.url
import com.konradjurkowski.moviehub.core.navigation.AppNavigator
import com.konradjurkowski.moviehub.core.utils.tools.DispatchersProvider
import com.konradjurkowski.moviehub.feature.movies.domain.repository.MovieRepository
import com.konradjurkowski.moviehub.feature.movies.navigation.MoviesDestination.MoviePreviewRoute
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.ise.MoviePreviewEvent
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.ise.MoviePreviewIntent
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.ise.MoviePreviewIntent.BackPressed
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.ise.MoviePreviewIntent.Refresh
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.ise.MoviePreviewIntent.VideoPressed
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.ise.MoviePreviewState
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.ise.MoviePreviewState.Error
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.ise.MoviePreviewState.Idle
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.ise.MoviePreviewState.Loading
import com.konradjurkowski.moviehub.feature.movies.presentation.preview.ise.MoviePreviewState.Success
import kotlinx.coroutines.launch

class MoviePreviewViewModel(
    private val movieRepository: MovieRepository,
    private val navigator: AppNavigator,
    private val dispatchersProvider: DispatchersProvider,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<MoviePreviewIntent, MoviePreviewState, MoviePreviewEvent>(initialState = Idle) {

    private var args = savedStateHandle.toRoute<MoviePreviewRoute>()

    init {
        loadInitialData()
    }

    override fun processIntent(intent: MoviePreviewIntent) {
        when (intent) {
            BackPressed -> navigator.back()
            Refresh -> loadInitialData()
            is VideoPressed -> navigator.openUrl(intent.video.url)
        }
    }

    private fun loadInitialData() {
        updateState { Loading }
        viewModelScope.launch(dispatchersProvider.io) {
            when (val result = movieRepository.getMovieById(movieId = args.movieId)) {
                is Response.Success -> {
                    updateState { Success(result.data) }
                }

                is Response.Failure -> {
                    updateState { Error(result.error) }
                }
            }
        }
    }
}
