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
sealed class DrawMediaState {
    data object Idle : DrawMediaState()
    data object Loading : DrawMediaState()
    data class Error(val error: Throwable? = null) : DrawMediaState()
    data class Success(
        val firebaseMovies: List<FirebaseMovie> = emptyList(),
        val firebaseSeries: List<FirebaseSeries> = emptyList(),
        val selectedMovie: FirebaseMovie? = null,
        val selectedSeries: FirebaseSeries? = null,
        val shakeCount: Int = 0
    ) : DrawMediaState()
}
