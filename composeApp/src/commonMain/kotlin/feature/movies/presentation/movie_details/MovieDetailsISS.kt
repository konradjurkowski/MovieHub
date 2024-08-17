package feature.movies.presentation.movie_details

import core.architecture.MviIntent
import core.architecture.MviSideEffect
import core.architecture.MviState
import feature.auth.domain.AppUser
import feature.movies.domain.model.CastData
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
}

@MviSideEffect
sealed class MovieDetailsSideEffect {
    data object NavigateBack : MovieDetailsSideEffect()
    data class GoToAddComment(val firebaseRating: FirebaseRating? = null) : MovieDetailsSideEffect()
    data object ShowLoader : MovieDetailsSideEffect()
    data object HideLoaderWithSuccess : MovieDetailsSideEffect()
    data class HideLoaderWithError(val error: Throwable) : MovieDetailsSideEffect()
}

@MviState
data class MovieDetailsState(
    val movie: MovieDetails? = null,
    val firebaseMovie: FirebaseMovie? = null,
    val castData: CastData? = null,
    val users: List<AppUser> = emptyList(),
    val selectedTab: Int = 0,
    val isLoading: Boolean = false,
)
