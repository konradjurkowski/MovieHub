package feature.movies.presentation.movies

import core.architecture.MviIntent
import core.architecture.MviSideEffect
import core.utils.Resource
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

typealias MoviesState = Resource<List<FirebaseMovie>>
