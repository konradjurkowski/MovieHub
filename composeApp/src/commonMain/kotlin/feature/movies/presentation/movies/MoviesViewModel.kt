package feature.movies.presentation.movies

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.tools.dispatcher.DispatchersProvider
import core.utils.Resource
import feature.movies.data.repository.MovieRepository
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val repository: MovieRepository,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<MoviesIntent, MoviesSideEffect, MoviesState>() {

    override fun getDefaultState() = Resource.Idle

    override fun processIntent(intent: MoviesIntent) {
        when (intent) {
            MoviesIntent.Refresh -> getMovies()
            is MoviesIntent.MoviePressed -> sendSideEffect(MoviesSideEffect.GoToMovieDetail(intent.movie))
            is MoviesIntent.AddMoviePressed -> sendSideEffect(MoviesSideEffect.GoToAddMovie)
        }
    }

    fun getMovies() {
        if (viewState.value == Resource.Idle) updateViewState { Resource.Loading }
        screenModelScope.launch(dispatchersProvider.io) {
            val result = repository.getTopRatedFirebaseMovies()
            updateViewState { result }
        }
    }
}
