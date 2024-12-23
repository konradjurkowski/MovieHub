package feature.movies.presentation.movies

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.tools.dispatcher.DispatchersProvider
import core.tools.event_bus.EventBus
import core.tools.event_bus.RefreshMovieList
import core.utils.Resource
import feature.movies.data.repository.MovieRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val repository: MovieRepository,
    private val eventBus: EventBus,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<MoviesIntent, MoviesSideEffect, MoviesState>() {

    init {
        initListener()
        getMovies()
    }
    override fun getDefaultState() = Resource.Idle

    override fun processIntent(intent: MoviesIntent) {
        when (intent) {
            MoviesIntent.Refresh -> getMovies()
            is MoviesIntent.MoviePressed -> sendSideEffect(MoviesSideEffect.GoToMovieDetail(intent.movie))
            is MoviesIntent.AddMoviePressed -> sendSideEffect(MoviesSideEffect.GoToAddMovie)
        }
    }

    private fun getMovies() {
        updateViewState { Resource.Loading }
        screenModelScope.launch(dispatchersProvider.io) {
            val result = repository.getTopRatedFirebaseMovies()
            updateViewState { result }
        }
    }

    private fun initListener() {
        screenModelScope.launch {
            eventBus.events
                .filterIsInstance<RefreshMovieList>()
                .collectLatest {
                    getMovies()
                }
        }
    }
}
