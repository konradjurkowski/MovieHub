package feature.home.presentation.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.components.other.RegularSpacer
import core.utils.Dimens
import feature.home.presentation.draw.DrawType
import feature.movies.domain.model.FirebaseMovie
import feature.series.domain.model.FirebaseSeries

@Composable
fun HomeDrawMediaSection(
    modifier: Modifier = Modifier,
    movies: List<FirebaseMovie>,
    series: List<FirebaseSeries>,
    onDrawMoviePressed: () -> Unit,
    onDrawSeriesPressed: () -> Unit,
) {
    if (movies.isNotEmpty() || series.isNotEmpty()) {
        Row(
            modifier = modifier
                .padding(horizontal = Dimens.padding16)
                .padding(bottom = Dimens.padding16),
        ) {
            HomeDrawMediaButton(
                modifier = Modifier.weight(1f),
                drawType = DrawType.MOVIE,
                onPressed = onDrawMoviePressed,
            )
            if (movies.isNotEmpty() && series.isNotEmpty()) RegularSpacer()
            HomeDrawMediaButton(
                modifier = Modifier.weight(1f),
                drawType = DrawType.SERIES,
                onPressed = onDrawSeriesPressed,
            )
        }
    }
}
