package feature.movies.presentation.movies

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.model.Response
import core.tools.dispatcher.DispatchersProvider
import feature.movies.data.repository.MovieRepository
import feature.movies.presentation.movies.MoviesIntent.AddMoviePressed
import feature.movies.presentation.movies.MoviesIntent.MoviePressed
import feature.movies.presentation.movies.MoviesIntent.Refresh
import feature.movies.presentation.movies.MoviesSideEffect.GoToAddMovie
import feature.movies.presentation.movies.MoviesSideEffect.GoToMovieDetail
import feature.movies.presentation.movies.MoviesState.Idle
import feature.movies.presentation.movies.MoviesState.Loading
import feature.movies.presentation.movies.MoviesState.Success
import feature.movies.presentation.movies.MoviesState.Error
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job

class MoviesViewModel(
    private val repository: MovieRepository,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<MoviesIntent, MoviesSideEffect, MoviesState>() {

    private var loadMoviesJob : Job? = null

    override fun getDefaultState() = Idle

    override fun processIntent(intent: MoviesIntent) {
        when (intent) {
            Refresh -> getMovies()
            is MoviePressed -> sendSideEffect(GoToMovieDetail(intent.movie))
            is AddMoviePressed -> sendSideEffect(GoToAddMovie)
        }
    }

    fun getMovies() {
        if (loadMoviesJob?.isActive == true) return
        if (viewState.value.isIdle()) updateViewState { Loading }

        loadMoviesJob = screenModelScope.launch(dispatchersProvider.io) {
            when (val result = repository.getFirebaseMovies()) {
                is Response.Success -> updateViewState { Success(result.data) }
                is Response.Failure -> updateViewState { Error(result.error) }
            }
        }
    }
}
