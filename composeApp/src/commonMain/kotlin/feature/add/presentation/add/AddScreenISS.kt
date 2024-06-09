package feature.add.presentation.add

import core.architecture.MviIntent
import core.architecture.MviSideEffect
import core.architecture.MviState

@MviIntent
sealed class AddScreenIntent {
    data object AddMoviePressed : AddScreenIntent()
    data object AddSeriesPressed : AddScreenIntent()
}

@MviSideEffect
sealed class AddScreenSideEffect {
    data object GoToAddMovie : AddScreenSideEffect()
    data object GoToAddSeries : AddScreenSideEffect()
}

@MviState
object AddScreenState
