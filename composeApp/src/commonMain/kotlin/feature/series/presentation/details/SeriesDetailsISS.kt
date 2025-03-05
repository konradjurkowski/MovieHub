package feature.series.presentation.details

import core.architecture.MviIntent
import core.architecture.MviSideEffect
import core.architecture.MviState
import feature.auth.domain.AppUser
import core.model.media.CastData
import feature.movies.domain.model.FirebaseRating
import feature.series.domain.model.FirebaseSeries
import feature.series.domain.model.SeriesDetails

@MviIntent
sealed class SeriesDetailsIntent {
    data object BackPressed : SeriesDetailsIntent()
    data object Refresh : SeriesDetailsIntent()
    data class AddCommentPressed(val firebaseRating: FirebaseRating? = null) : SeriesDetailsIntent()
    data class DeleteCommentPressed(val firebaseRating: FirebaseRating) : SeriesDetailsIntent()
    data class SetTab(val tab: Int) : SeriesDetailsIntent()
}

@MviSideEffect
sealed class SeriesDetailsSideEffect {
    data object NavigateBack : SeriesDetailsSideEffect()
    data class GoToAddComment(val firebaseRating: FirebaseRating? = null) : SeriesDetailsSideEffect()
    data object ShowLoader : SeriesDetailsSideEffect()
    data object HideLoaderWithSuccess : SeriesDetailsSideEffect()
    data class HideLoaderWithError(val error: Throwable) : SeriesDetailsSideEffect()
}

@MviState
sealed class SeriesDetailsState {
    data object Idle : SeriesDetailsState()
    data object Loading : SeriesDetailsState()
    data class Error(val error: Throwable? = null) : SeriesDetailsState()
    data class Success(
        val series: SeriesDetails,
        val firebaseSeries: FirebaseSeries,
        val castData: CastData,
        val users: List<AppUser> = emptyList(),
        val selectedTab: Int = 0,
    ) : SeriesDetailsState()
}
