package com.konradjurkowski.moviehub.feature.movies.presentation.preview.comp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.konradjurkowski.moviehub.core.domain.model.media.Video
import com.konradjurkowski.moviehub.core.presentation.comp.media.details.MediaDetailsBackground
import com.konradjurkowski.moviehub.core.presentation.comp.media.details.MediaDetailsInfo
import com.konradjurkowski.moviehub.core.presentation.comp.other.RegularSpacer
import com.konradjurkowski.moviehub.core.utils.helpers.rememberScreenSize
import com.konradjurkowski.moviehub.feature.movies.domain.model.MovieDetails
import com.konradjurkowski.moviehub.feature.movies.presentation.comp.MovieInfoTab
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.minutes_label
import org.jetbrains.compose.resources.stringResource

@Composable
fun MoviePreviewSuccess(
    modifier: Modifier = Modifier,
    movie: MovieDetails,
    onBackClick: () -> Unit,
    onVideoClick: (Video) -> Unit,
) {
    val screenSize = rememberScreenSize()
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Box(modifier = Modifier.height(screenSize.height * 0.6f)) {
            MediaDetailsBackground(backgroundUrl = movie.backgroundUrl)
            MediaDetailsInfo(
                title = movie.title,
                releaseDate = movie.releaseDate ?: "",
                duration = "${movie.runtime} ${stringResource(Res.string.minutes_label)}",
                genre = movie.genres.firstOrNull()?.name ?: "",
                posterUrl = movie.posterUrl,
                onBackClick = onBackClick,
            )
        }
        RegularSpacer()
        MovieInfoTab(
            movie = movie,
            onVideoClick = onVideoClick,
        )
    }
}
