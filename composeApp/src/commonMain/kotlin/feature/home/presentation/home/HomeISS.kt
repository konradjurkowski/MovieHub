package feature.home.presentation.home

import core.architecture.MviIntent
import core.architecture.MviSideEffect
import core.architecture.MviState
import feature.auth.domain.AppUser
import feature.movies.domain.model.FirebaseMovie

@MviIntent
sealed class HomeIntent {
    data class MoviePressed(val movie: FirebaseMovie) : HomeIntent()
    data object OnUserPressed : HomeIntent()
}

@MviSideEffect
sealed class HomeSideEffect {
    data class GoToMovieDetail(val movie: FirebaseMovie) : HomeSideEffect()
    data object GoToProfileTab : HomeSideEffect()
}

@MviState
data class HomeState(
    val isLoading: Boolean = false,
    val lastUpdatedMovies: List<FirebaseMovie>? = null,
    val appUser: AppUser? = null,
)
