package com.konradjurkowski.moviehub.feature.movies.presentation.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.konradjurkowski.moviehub.core.domain.model.media.Crew
import com.konradjurkowski.moviehub.core.domain.model.media.Video
import com.konradjurkowski.moviehub.core.presentation.comp.media.details.MediaCastList
import com.konradjurkowski.moviehub.core.presentation.comp.media.details.MediaVideoList
import com.konradjurkowski.moviehub.core.presentation.comp.other.SmallSpacer
import com.konradjurkowski.moviehub.core.presentation.comp.text.SectionTitle
import com.konradjurkowski.moviehub.core.presentation.theme.withA70
import com.konradjurkowski.moviehub.core.presentation.theme.withA80
import com.konradjurkowski.moviehub.core.utils.Dimens
import com.konradjurkowski.moviehub.feature.movies.domain.model.MovieDetails
import com.konradjurkowski.moviehub.feature.movies.domain.model.director
import com.konradjurkowski.moviehub.feature.movies.domain.model.trailers
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.director_label
import moviehub.composeapp.generated.resources.movie_screen_details_movie_info
import org.jetbrains.compose.resources.stringResource

@Composable
fun MovieInfoTab(
    modifier: Modifier = Modifier,
    movie: MovieDetails,
    onVideoClick: (Video) -> Unit,
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
        DirectorSection(director = movie.director)
        MediaCastList(castList = movie.cast)
        MediaVideoList(videoList = movie.trailers, onVideoClick = onVideoClick)
    }
}

@Composable
fun DirectorSection(
    modifier: Modifier = Modifier,
    director: Crew? = null,
) {
    if (director == null) return

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.padding16)
            .padding(top = Dimens.padding16),
    ) {
        Text(
            text = director.name,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold,
        )
        SmallSpacer()
        Text(
            text = stringResource(Res.string.director_label),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onBackground.withA70(),
            fontWeight = FontWeight.Normal,
        )
    }
}
