package feature.rating.presentation.add_rating

import core.architecture.MviIntent
import core.architecture.MviSideEffect
import core.architecture.MviState
import feature.movies.domain.model.FirebaseRating

@MviIntent
sealed class AddRatingIntent {
    data class LoadInitialData(val firebaseRating: FirebaseRating?) : AddRatingIntent()
    data class CommentUpdated(val comment: String) : AddRatingIntent()
    data class RatingUpdated(val rating: Double) : AddRatingIntent()
    data class Submit(
        val isMovie: Boolean,
        val rating: Double,
        val comment: String,
    ) : AddRatingIntent()
}

@MviSideEffect
sealed class AddRatingSideEffect {
    data class ShowError(val error: Throwable) : AddRatingSideEffect()
    data object Success : AddRatingSideEffect()
}

@MviState
data class AddRatingState(
    val comment: String = "",
    val rating: Double = 0.0,
    val isLoading: Boolean = false,
)
