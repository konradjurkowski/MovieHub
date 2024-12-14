package core.components.media.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import core.components.button.BoxButton
import core.components.image.AnyImage
import core.components.media.MediaInfo
import core.components.media.MediaRating
import core.components.other.RegularSpacer
import core.components.other.SmallSpacer
import core.theme.withA70
import core.utils.Dimens
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.ic_calendar
import moviehub.composeapp.generated.resources.ic_clock
import moviehub.composeapp.generated.resources.ic_ticket

@Composable
fun MediaDetailsInfo(
    modifier: Modifier = Modifier,
    title: String,
    releaseDate: String,
    duration: String,
    genre: String,
    posterPath: String?,
    rating: Double = 0.0,
    onRatingPressed: (() -> Unit)? = null,
    onBackPressed: () -> Unit,
) {
    Column(modifier = modifier
        .fillMaxSize()
        .padding(horizontal = Dimens.padding16),
    ) {
        BoxButton(modifier = Modifier.safeDrawingPadding()) { onBackPressed() }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Card(
                modifier = Modifier
                    .width(100.dp)
                    .height(140.dp),
                shape = RoundedCornerShape(Dimens.radius12),
                elevation = CardDefaults.cardElevation(defaultElevation = Dimens.defaultElevation),
            ) {
                AnyImage(
                    modifier = Modifier.fillMaxSize(),
                    image = posterPath,
                )
            }
            RegularSpacer()
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
            )
            RegularSpacer()
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    MediaInfo(
                        modifier = Modifier.padding(end = Dimens.padding8),
                        iconRes = Res.drawable.ic_calendar,
                        text = releaseDate,
                    )
                    VerticalDivider(
                        modifier = Modifier.height(Dimens.padding16),
                        color = MaterialTheme.colorScheme.onBackground.withA70(),
                    )
                    MediaInfo(
                        modifier = Modifier.padding(horizontal = Dimens.padding8),
                        iconRes = Res.drawable.ic_clock,
                        text = duration,
                    )
                    VerticalDivider(
                        modifier = Modifier.height(Dimens.padding16),
                        color = MaterialTheme.colorScheme.onBackground.withA70(),
                    )
                    MediaInfo(
                        modifier = Modifier.padding(start = Dimens.padding8),
                        iconRes = Res.drawable.ic_ticket,
                        text = genre,
                    )
                }
                if (onRatingPressed != null) {
                    SmallSpacer()
                    MediaRating(rating = rating, onClick = onRatingPressed)
                }
            }
        }
    }
}
