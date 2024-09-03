package feature.movies.presentation.info_tab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import core.components.media.info.MediaInfoItem
import core.components.media.production_companies.MediaProductionCompanyList
import core.components.other.RegularSpacer
import core.theme.withA80
import core.utils.Dimens
import feature.movies.domain.model.CastData
import feature.movies.domain.model.MovieDetails
import feature.movies.domain.model.getDirector
import feature.movies.domain.model.getWriter
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.director_label
import moviehub.composeapp.generated.resources.writer_label
import org.jetbrains.compose.resources.stringResource

@Composable
fun MovieInfoTab(
    modifier: Modifier = Modifier,
    movie: MovieDetails,
    castData: CastData,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.padding16),
        verticalArrangement = Arrangement.Top,
    ) {
        Text(
            text = movie.overview,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.withA80(),
        )
        RegularSpacer()
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            castData.getDirector()?.let {
                MediaInfoItem(
                    title = it.name,
                    subtitle = stringResource(Res.string.director_label),
                )
            }
            castData.getWriter()?.let {
                MediaInfoItem(
                    title = it.name,
                    subtitle = stringResource(Res.string.writer_label),
                )
            }
        }
        RegularSpacer()
        MediaProductionCompanyList(movie.productionCompanies)
    }
}
