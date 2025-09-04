package com.konradjurkowski.moviehub.feature.movies.presentation.preview.ise

import com.konradjurkowski.moviehub.core.architecture.MviEvent
import com.konradjurkowski.moviehub.core.architecture.MviIntent
import com.konradjurkowski.moviehub.core.architecture.MviState
import com.konradjurkowski.moviehub.core.domain.model.ActionState
import com.konradjurkowski.moviehub.core.domain.model.media.Video
import com.konradjurkowski.moviehub.feature.movies.domain.model.MovieDetails

@MviIntent
sealed class MoviePreviewIntent {
    data object BackPressed : MoviePreviewIntent()
    data class MovieAddPressed(val movie: MovieDetails) : MoviePreviewIntent()
    data object Refresh : MoviePreviewIntent()
    data class VideoPressed(val video: Video) : MoviePreviewIntent()
}

@MviEvent
sealed class MoviePreviewEvent {
    data object ShowSuccess : MoviePreviewEvent()
    data class ShowError(val error: Throwable) : MoviePreviewEvent()
}

@MviState
sealed class MoviePreviewState {
    data object Idle : MoviePreviewState()
    data object Loading : MoviePreviewState()
    data class Error(val error: Throwable? = null) : MoviePreviewState()
    data class Success(
        val movie: MovieDetails,
        val movieAdded: Boolean = false,
        val addState: ActionState = ActionState.Idle,
    ) : MoviePreviewState()

    fun getSuccess(): Success? = this as? Success
    fun isDataLoaded() = this is Success
    fun isMovieAdded() = getSuccess()?.movieAdded ?: false
    fun isAdding() = getSuccess()?.addState?.isLoading() ?: false
}
