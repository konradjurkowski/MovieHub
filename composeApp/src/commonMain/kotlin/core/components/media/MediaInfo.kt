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
import core.components.other.TinySpacer
import core.theme.withA70
import core.utils.Dimens
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun MediaInfo(
    modifier: Modifier = Modifier,
    iconRes: DrawableResource,
    text: String,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(Dimens.icon16),
            painter = painterResource(iconRes),
            contentDescription = "MovieInfo icon",
            tint = MaterialTheme.colorScheme.onBackground.withA70(),
        )
        TinySpacer()
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground.withA70(),
        )
    }
}
