package feature.movies.presentation.movies

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.model.Response
import core.tools.dispatcher.DispatchersProvider
import feature.movies.data.repository.MovieRepository
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val repository: MovieRepository,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<MoviesIntent, MoviesSideEffect, MoviesState>() {

    override fun getDefaultState() = MoviesState.Idle

    override fun processIntent(intent: MoviesIntent) {
        when (intent) {
            MoviesIntent.Refresh -> getMovies()
            is MoviesIntent.MoviePressed -> sendSideEffect(MoviesSideEffect.GoToMovieDetail(intent.movie))
            is MoviesIntent.AddMoviePressed -> sendSideEffect(MoviesSideEffect.GoToAddMovie)
        }
    }

    fun getMovies() {
        if (viewState.value == MoviesState.Idle) updateViewState { MoviesState.Loading }
        screenModelScope.launch(dispatchersProvider.io) {
            when (val result = repository.getFirebaseMovies()) {
                is Response.Success -> updateViewState { MoviesState.Success(result.data) }
                is Response.Failure -> updateViewState { MoviesState.Error(result.error) }
            }
        }
    }
}
