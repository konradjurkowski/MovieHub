package feature.movies.presentation.movies

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import core.utils.FailureResponseException
import core.utils.Resource
import feature.movies.data.api.dto.Genre
import feature.movies.data.repository.MovieRepository
import feature.movies.domain.model.MoviesOverview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

typealias MoviesState = Resource<MoviesOverview>

class MoviesViewModel(
    private val repository: MovieRepository
) : ScreenModel {
    private val _state = MutableStateFlow<MoviesState>(Resource.Idle)
    val state: StateFlow<MoviesState> = _state.asStateFlow()

    init {
        fetchMovies()
    }

    fun fetchMovies() {
        if (_state.value.isLoading()) return

        _state.value = Resource.Loading
        screenModelScope.launch(Dispatchers.IO) {
            val popularFuture = async { repository.getPopularMovies() }
            val topRatedFuture = async { repository.getTopRatedMovies() }
            val genresFuture = async {
                when (localMoviesGenresList.isEmpty()) {
                    true -> repository.getGenres()
                    false -> Resource.Success(localMoviesGenresList)
                }
            }

            val popularResult = popularFuture.await()
            val topRatedResult = topRatedFuture.await()
            val genresResult = genresFuture.await()

            if (popularResult.isSuccess() && topRatedResult.isSuccess() && genresResult.isSuccess()) {
                val popularMovies = popularResult.getSuccess() ?: emptyList()
                val topRatedMovies = topRatedResult.getSuccess() ?: emptyList()
                val genresList = genresResult.getSuccess() ?: emptyList()
                localMoviesGenresList = genresList

                val moviesOverview = MoviesOverview(popularMovies, topRatedMovies)
                _state.value = Resource.Success(moviesOverview)
            } else {
                _state.value = Resource.Failure(FailureResponseException())
            }
        }
    }
}

// TODO STORE SOMEWHERE
var localMoviesGenresList: List<Genre> = emptyList()
