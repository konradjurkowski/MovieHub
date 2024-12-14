package feature.movies.presentation.preview.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.components.media.details.MediaDetailsBackground
import core.components.media.details.MediaDetailsInfo
import core.components.other.RegularSpacer
import core.utils.LocalDateTimeFormatter
import core.utils.getScreenSizeInfo
import feature.movies.domain.model.CastData
import feature.movies.domain.model.MovieDetails
import feature.movies.presentation.info_tab.MovieInfoTab
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.minutes_label
import org.jetbrains.compose.resources.stringResource

@Composable
fun MoviePreviewSuccess(
    modifier: Modifier = Modifier,
    movie: MovieDetails,
    castData: CastData,
    onBackPressed: () -> Unit,
) {
    val dateTimeFormatter = LocalDateTimeFormatter.current
    val screenSize = getScreenSizeInfo()
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        Box(modifier = Modifier.height(screenSize.height * 0.6f)) {
            MediaDetailsBackground(backgroundPath = movie.backdropPath)
            MediaDetailsInfo(
                title = movie.title,
                releaseDate = dateTimeFormatter.formatToYear(movie.releaseDate),
                duration = "${movie.runtime} ${stringResource(Res.string.minutes_label)}",
                genre = movie.genres.firstOrNull()?.name ?: "",
                posterPath = movie.posterPath,
                onBackPressed = onBackPressed,
            )
        }
        RegularSpacer()
        MovieInfoTab(movie = movie, castData = castData)
    }
}
