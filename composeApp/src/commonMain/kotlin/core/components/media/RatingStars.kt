package core.components.media

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import core.components.other.SmallSpacer
import core.utils.Dimens
import core.utils.round
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.ic_star
import org.jetbrains.compose.resources.painterResource

@Composable
fun RatingStars(
    modifier: Modifier = Modifier,
    rating: Double,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(Dimens.icon14),
            painter = painterResource(Res.drawable.ic_star),
            contentDescription = "Rating Star",
            tint = MaterialTheme.colorScheme.primary,
        )
        SmallSpacer()
        Text(
            text = rating.round(1).toString(),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.ExtraLight,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}
