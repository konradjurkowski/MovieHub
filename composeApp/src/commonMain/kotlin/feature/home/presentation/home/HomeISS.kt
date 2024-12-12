package feature.home.presentation.home

import core.architecture.MviIntent
import core.architecture.MviSideEffect
import core.architecture.MviState
import feature.auth.domain.AppUser
import feature.movies.domain.model.FirebaseMovie

@MviIntent
sealed class HomeIntent {

}

@MviSideEffect
sealed class HomeSideEffect {

}

@MviState
data class HomeState(
    val isLoading: Boolean = false,
    val lastUpdatedMovies: List<FirebaseMovie> = emptyList(),
    val user: AppUser? = null,
)
