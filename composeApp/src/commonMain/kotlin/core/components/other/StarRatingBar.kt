import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import core.theme.withA10
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.ic_star
import moviehub.composeapp.generated.resources.ic_star_outline
import org.jetbrains.compose.resources.painterResource

@Composable
fun StarRatingBar(
    modifier: Modifier = Modifier,
    maxStars: Int = 5,
    rating: Double,
    onRatingChanged: (Double) -> Unit
) {
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val starSize = (maxWidth / maxStars)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            for (i in 1..maxStars) {
                val isSelected = i <= rating
                val (iconRes, color) = when (isSelected) {
                    true -> Res.drawable.ic_star to MaterialTheme.colorScheme.primary
                    false -> Res.drawable.ic_star_outline to MaterialTheme.colorScheme.onBackground.withA10()
                }
                Icon(
                    painter = painterResource(iconRes),
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier
                        .size(starSize)
                        .selectable(
                            selected = isSelected,
                            onClick = { onRatingChanged(i.toDouble()) },
                        )
                )
            }
        }
    }
}
