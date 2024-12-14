package feature.series.presentation.info_tab.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import core.components.image.AnyImage
import core.theme.withA40
import core.utils.Dimens
import feature.series.domain.model.Season
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.user_placeholder

@Composable
fun SeasonCard(
    modifier: Modifier = Modifier,
    season: Season,
) {
    Card(
        modifier = modifier
            .height(150.dp)
            .width(120.dp),
        shape = RoundedCornerShape(Dimens.radius4),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AnyImage(
                modifier = Modifier.fillMaxSize(),
                image = season.posterPath,
                placeholderRes = Res.drawable.user_placeholder,
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.withA40()),
            )
            Text(
                modifier = Modifier
                    .padding(Dimens.padding4)
                    .align(Alignment.BottomStart),
                text = season.name,
                style = MaterialTheme.typography.labelMedium,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}