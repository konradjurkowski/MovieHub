package feature.movies.presentation.details

import core.architecture.MviIntent
import core.architecture.MviSideEffect
import core.architecture.MviState
import feature.auth.domain.AppUser
import core.model.media.CastData
import core.model.media.Video
import feature.movies.domain.model.FirebaseMovie
import feature.movies.domain.model.FirebaseRating
import feature.movies.domain.model.MovieDetails

@MviIntent
sealed class MovieDetailsIntent {
    data object BackPressed : MovieDetailsIntent()
    data object Refresh : MovieDetailsIntent()
    data class AddCommentPressed(val firebaseRating: FirebaseRating? = null) : MovieDetailsIntent()
    data class DeleteCommentPressed(val firebaseRating: FirebaseRating) : MovieDetailsIntent()
    data class SetTab(val tab: Int) : MovieDetailsIntent()
    data class VideoPressed(val video: Video) : MovieDetailsIntent()
}

@MviSideEffect
sealed class MovieDetailsSideEffect {
    data object NavigateBack : MovieDetailsSideEffect()
    data class GoToAddComment(val firebaseRating: FirebaseRating? = null) : MovieDetailsSideEffect()
    data object ShowLoader : MovieDetailsSideEffect()
    data object HideLoaderWithSuccess : MovieDetailsSideEffect()
    data class HideLoaderWithError(val error: Throwable) : MovieDetailsSideEffect()
    data class OpenUrl(val url: String) : MovieDetailsSideEffect()
}

@MviState
sealed class MovieDetailsState {
    data object Idle : MovieDetailsState()
    data object Loading : MovieDetailsState()
    data class Error(val error: Throwable? = null) : MovieDetailsState()
    data class Success(
        val movie: MovieDetails,
        val firebaseMovie: FirebaseMovie,
        val castData: CastData,
        val users: List<AppUser> = emptyList(),
        val selectedTab: Int = 0,
    ) : MovieDetailsState()
}
