package core.components.movie

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.components.other.SmallSpacer
import core.theme.starColor
import core.utils.round

@Composable
fun RatingSection(
    modifier: Modifier = Modifier,
    rating: Double
) {
    Row(modifier = modifier) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "Star icon",
            tint = starColor
        )
        SmallSpacer()
        Text(
            text = rating.round(2).toString(),
            color = MaterialTheme.colorScheme.secondary
        )
    }
}
