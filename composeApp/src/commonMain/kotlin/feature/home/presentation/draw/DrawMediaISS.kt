package feature.home.presentation.draw

import core.architecture.MviIntent
import core.architecture.MviSideEffect
import core.architecture.MviState
import feature.movies.domain.model.FirebaseMovie
import feature.series.domain.model.FirebaseSeries

@MviIntent
sealed class DrawMediaIntent {
    data object OnShakePressed : DrawMediaIntent()
    data object TryAgainPressed : DrawMediaIntent()
    data class MoviePressed(val movie: FirebaseMovie) : DrawMediaIntent()
    data class SeriesPressed(val series: FirebaseSeries) : DrawMediaIntent()
}

@MviSideEffect
sealed class DrawMediaSideEffect {
    data class GoToMovieDetails(val movie: FirebaseMovie) : DrawMediaSideEffect()
    data class GoToSeriesDetails(val series: FirebaseSeries) : DrawMediaSideEffect()
}

@MviState
data class DrawMediaState(
    val isLoading: Boolean = false,
    val firebaseMovies: List<FirebaseMovie>? = null,
    val firebaseSeries: List<FirebaseSeries>? = null,
    val selectedMovie: FirebaseMovie? = null,
    val selectedSeries: FirebaseSeries? = null,
    val shakeCount: Int = 0,
)

fun DrawMediaState.isDataLoaded() = !isLoading && firebaseMovies != null && firebaseSeries != null
