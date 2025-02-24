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
data class HomeState(
    val isLoading: Boolean = false,
    val firebaseMovies: List<FirebaseMovie> = emptyList(),
    val lastUpdatedMovies: List<FirebaseMovie>? = null,
    val firebaseSeries: List<FirebaseSeries> = emptyList(),
    val lastUpdatedSeries: List<FirebaseSeries>? = null,
    val appUser: AppUser? = null,
)

fun HomeState.isDataLoaded() = !isLoading && lastUpdatedMovies != null && lastUpdatedSeries != null
