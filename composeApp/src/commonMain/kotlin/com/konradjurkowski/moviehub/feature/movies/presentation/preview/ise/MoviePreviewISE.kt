package com.konradjurkowski.moviehub.feature.movies.presentation.preview.ise

import com.konradjurkowski.moviehub.core.architecture.MviEvent
import com.konradjurkowski.moviehub.core.architecture.MviIntent
import com.konradjurkowski.moviehub.core.architecture.MviState
import com.konradjurkowski.moviehub.core.domain.model.media.Video
import com.konradjurkowski.moviehub.feature.movies.domain.model.MovieDetails

@MviIntent
sealed class MoviePreviewIntent {
    data object BackPressed : MoviePreviewIntent()
    data object Refresh : MoviePreviewIntent()
    data class VideoPressed(val video: Video) : MoviePreviewIntent()
}

@MviEvent
sealed class MoviePreviewEvent {

}

@MviState
sealed class MoviePreviewState {
    data object Idle : MoviePreviewState()
    data object Loading : MoviePreviewState()
    data class Error(val error: Throwable? = null) : MoviePreviewState()
    data class Success(val movie: MovieDetails) : MoviePreviewState()

    fun isLoaded() = this is Success
}
