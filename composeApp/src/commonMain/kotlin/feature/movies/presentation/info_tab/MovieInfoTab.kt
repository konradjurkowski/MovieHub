package feature.movies.presentation.info_tab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import core.components.media.cast.MediaCastList
import core.components.media.info.MediaInfoItem
import core.components.media.company.MediaCompanyList
import core.components.other.RegularSpacer
import core.components.text.SectionTitle
import core.theme.withA80
import core.utils.Dimens
import feature.movies.domain.model.CastData
import feature.movies.domain.model.MovieDetails
import feature.movies.domain.model.getDirector
import feature.movies.domain.model.getWriter
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.director_label
import moviehub.composeapp.generated.resources.movie_screen_details_movie_info
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
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.Top,
    ) {
        SectionTitle(
            modifier = Modifier.padding(horizontal = Dimens.padding16),
            title = stringResource(Res.string.movie_screen_details_movie_info),
        )
        Text(
            modifier = Modifier
                .padding(horizontal = Dimens.padding16)
                .padding(top = Dimens.padding8),
            text = movie.overview,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.withA80(),
        )
        Row(
            modifier = Modifier
                .padding(horizontal = Dimens.padding16)
                .padding(top = Dimens.padding16)
                .fillMaxWidth(),
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
        MediaCastList(castList = castData.cast)
        MediaCompanyList(companyList = movie.productionCompanies)
        RegularSpacer()
    }
}
