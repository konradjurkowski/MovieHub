package feature.series.presentation.preview.components

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
import feature.series.domain.model.SeriesDetails
import feature.series.presentation.info_tab.SeriesInfoTab
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.seasons_label
import org.jetbrains.compose.resources.stringResource

@Composable
fun SeriesPreviewSuccess(
    modifier: Modifier = Modifier,
    series: SeriesDetails,
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
            MediaDetailsBackground(backgroundPath = series.backdropPath)
            MediaDetailsInfo(
                title = series.name,
                releaseDate = dateTimeFormatter.formatToYear(series.firstAirDate),
                duration = "${series.seasons.size} ${stringResource(Res.string.seasons_label)}",
                genre = series.genres.firstOrNull()?.name ?: "",
                posterPath = series.posterPath,
                onBackPressed = onBackPressed,
            )
        }
        RegularSpacer()
        SeriesInfoTab(series = series, castData = castData)
    }
}
