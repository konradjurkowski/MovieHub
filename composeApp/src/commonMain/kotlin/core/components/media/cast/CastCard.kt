package core.components.media.cast

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import core.components.image.NetworkImage
import core.components.other.SmallSpacer
import core.theme.withA10
import core.theme.withA60
import core.utils.Dimens
import feature.movies.domain.model.Cast
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.user_placeholder

@Composable
fun CastCard(
    modifier: Modifier = Modifier,
    cast: Cast,
) {
    Surface(
        modifier = modifier
            .aspectRatio(1f)
            .padding(Dimens.padding8),
        shape = RoundedCornerShape(Dimens.radius16),
        color = MaterialTheme.colorScheme.onBackground.withA10(),
    ) {
        Column(
            modifier = Modifier.padding(Dimens.padding8),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            NetworkImage(
                modifier = Modifier
                    .size(85.dp)
                    .clip(RoundedCornerShape(Dimens.radius16)),
                imageUrl = cast.profilePath,
                placeholderRes = Res.drawable.user_placeholder,
            )
            SmallSpacer()
            Text(
                text = cast.name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
            )
            Text(
                text = cast.character ?: "",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground.withA60(),
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
            )
        }
    }
}
