package com.konradjurkowski.moviehub.feature.movies.presentation.movies

import androidx.lifecycle.viewModelScope
import com.konradjurkowski.moviehub.core.architecture.BaseViewModel
import com.konradjurkowski.moviehub.core.domain.model.Response
import com.konradjurkowski.moviehub.core.navigation.AppNavigator
import com.konradjurkowski.moviehub.core.utils.coroutines.DispatchersProvider
import com.konradjurkowski.moviehub.feature.movies.domain.repository.MovieRepository
import com.konradjurkowski.moviehub.feature.movies.navigation.MoviesDestination.AddMovieRoute
import com.konradjurkowski.moviehub.feature.movies.presentation.movies.MoviesIntent.AddMovieClick
import com.konradjurkowski.moviehub.feature.movies.presentation.movies.MoviesIntent.MovieClick
import com.konradjurkowski.moviehub.feature.movies.presentation.movies.MoviesIntent.Refresh
import com.konradjurkowski.moviehub.feature.movies.presentation.movies.MoviesState.Idle
import com.konradjurkowski.moviehub.feature.movies.presentation.movies.MoviesState.Loading
import com.konradjurkowski.moviehub.feature.movies.presentation.movies.MoviesState.Success
import com.konradjurkowski.moviehub.feature.movies.presentation.movies.MoviesState.Error
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val repository: MovieRepository,
    private val navigator: AppNavigator,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<MoviesIntent, MoviesState, MoviesEvent>(initialState = Idle) {

    private var getMoviesJob: Job? = null

    override fun processIntent(intent: MoviesIntent) = when (intent) {
        AddMovieClick -> navigator.push(AddMovieRoute)
        is MovieClick -> {}
        Refresh -> getMovies()
    }

    fun getMovies() {
        if (getMoviesJob?.isActive == true) return
        if (viewState.value.isIdle) updateState { Loading }

        getMoviesJob = viewModelScope.launch(dispatchersProvider.io) {
            when (val result = repository.getMovieLeaderboardPreview(groupId = 1)) {
                is Response.Success -> updateState { Success(movies = result.data) }
                is Response.Failure -> updateState { Error(error = result.error) }
            }
        }
    }
}
