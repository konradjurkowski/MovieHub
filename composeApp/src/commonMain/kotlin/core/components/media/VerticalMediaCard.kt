package core.components.media

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import core.components.movie.GenresListSection
import core.components.movie.RatingSection
import core.components.other.RegularSpacer
import core.components.other.SmallSpacer
import core.utils.Dimens
import core.utils.toDp
import feature.movies.data.api.dto.Genre
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun VerticalMediaCard(
    modifier: Modifier = Modifier,
    title: String,
    imageUrl: String,
    rating: Double,
    genres: List<Genre>
) {
    Row(
        modifier = modifier
            .padding(
                vertical = Dimens.smallPadding,
                horizontal = Dimens.regularPadding
            )
            .fillMaxWidth(),
    ) {
        Card(
            modifier = Modifier
                .width(Dimens.verticalMediaCardWidth)
                .height(Dimens.verticalMediaCardHeight),
            elevation = CardDefaults.cardElevation(defaultElevation = Dimens.defaultElevation)
        ) {
            KamelImage(
                resource = asyncPainterResource(data = imageUrl),
                contentDescription = "Media Card",
                contentScale = ContentScale.Crop
            )
        }
        RegularSpacer()
        Column {
            Text(
                modifier = Modifier
                    .fillMaxSize(),
                text = title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            SmallSpacer()
            RatingSection(rating = rating)
            SmallSpacer()
            GenresListSection(genres = genres)
        }
    }
}

@Composable
fun VerticalMediaCardPlaceholder(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(
                vertical = Dimens.smallPadding,
                horizontal = Dimens.regularPadding
            )
            .fillMaxWidth(),
    ) {
        Card(
            modifier = Modifier
                .width(Dimens.verticalMediaCardWidth)
                .height(Dimens.verticalMediaCardHeight),
            elevation = CardDefaults.cardElevation(defaultElevation = Dimens.defaultElevation)
        ) {}
        RegularSpacer()
        Column {
            Card(
                modifier = Modifier
                    .height(MaterialTheme.typography.titleMedium.lineHeight.toDp())
                    .fillMaxWidth(),
                shape = RoundedCornerShape(Dimens.tinyCornerRadius),
                elevation = CardDefaults.cardElevation(defaultElevation = Dimens.defaultElevation)
            ) {}
            SmallSpacer()
            Card(
                modifier = Modifier
                    .height(MaterialTheme.typography.bodyMedium.lineHeight.toDp())
                    .fillMaxWidth(0.5f),
                shape = RoundedCornerShape(Dimens.tinyCornerRadius),
                elevation = CardDefaults.cardElevation(defaultElevation = Dimens.defaultElevation)
            ) {}
        }
    }
}
