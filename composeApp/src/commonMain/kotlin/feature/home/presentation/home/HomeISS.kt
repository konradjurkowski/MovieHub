package feature.home.presentation.home

import core.architecture.MviIntent
import core.architecture.MviSideEffect
import core.architecture.MviState
import feature.auth.domain.AppUser
import feature.home.presentation.draw.DrawType
import feature.movies.domain.model.FirebaseMovie
import feature.series.domain.model.FirebaseSeries

@MviIntent
sealed class HomeIntent {
    data class MoviePressed(val movie: FirebaseMovie) : HomeIntent()
    data class SeriesPressed(val series: FirebaseSeries) : HomeIntent()
    data object TryAgainPressed : HomeIntent()
    data object OnDrawMoviePressed : HomeIntent()
    data object OnDrawSeriesPressed : HomeIntent()
    data object OnUserPressed : HomeIntent()
}

@MviSideEffect
sealed class HomeSideEffect {
    data class GoToMovieDetails(val movie: FirebaseMovie) : HomeSideEffect()
    data class GoToSeriesDetails(val series: FirebaseSeries) : HomeSideEffect()
    data class GoToDrawMedia(val drawType: DrawType) : HomeSideEffect()
    data object GoToProfileTab : HomeSideEffect()
}

@MviState
sealed class HomeState {
    data object Idle : HomeState()
    data object Loading : HomeState()
    data class Error(val error: Throwable? = null) : HomeState()
    data class Success(
        val firebaseMovies: List<FirebaseMovie>,
        val firebaseSeries: List<FirebaseSeries>,
        val lastUpdatedMovies: List<FirebaseMovie>,
        val lastUpdatedSeries: List<FirebaseSeries>,
        val appUser: AppUser? = null,
    ) : HomeState()

    fun isIdle() = this is Idle
}
