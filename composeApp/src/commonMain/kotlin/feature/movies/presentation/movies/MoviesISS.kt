package feature.movies.presentation.movies

import core.architecture.MviIntent
import core.architecture.MviSideEffect
import core.architecture.MviState
import feature.movies.domain.model.FirebaseMovie

@MviIntent
sealed class MoviesIntent {
    data class MoviePressed(val movie: FirebaseMovie) : MoviesIntent()
    data object AddMoviePressed : MoviesIntent()
    data object Refresh : MoviesIntent()
}

@MviSideEffect
sealed class MoviesSideEffect {
    data class GoToMovieDetail(val movie: FirebaseMovie) : MoviesSideEffect()
    data object GoToAddMovie : MoviesSideEffect()
}

@MviState
sealed class MoviesState {
    data object Idle : MoviesState()
    data object Loading : MoviesState()
    data class Error(val error: Throwable? = null) : MoviesState()
    data class Success(val movies: List<FirebaseMovie>) : MoviesState()
}
