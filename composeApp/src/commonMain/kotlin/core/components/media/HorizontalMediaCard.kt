package core.components.media

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import core.components.other.TinySpacer
import core.utils.Dimens
import core.utils.toDp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun HorizontalMediaCard(
    modifier: Modifier = Modifier,
    title: String,
    imageUrl: String
) {
    Column(
        modifier = modifier
            .width(Dimens.horizontalMediaCardWidth)
            .height(Dimens.horizontalMediaCardHeight)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.horizontalMediaCardImageHeight),
            elevation = CardDefaults.cardElevation(defaultElevation = Dimens.defaultElevation)
        ) {
            KamelImage(
                resource = asyncPainterResource(data = imageUrl),
                contentDescription = "Media Card",
                contentScale = ContentScale.Crop
            )
        }
        TinySpacer()
        Text(
            modifier = Modifier
                .fillMaxSize(),
            text = title,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun HorizontalMediaCardPlaceholder(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .width(Dimens.horizontalMediaCardWidth)
            .height(Dimens.horizontalMediaCardHeight)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.horizontalMediaCardImageHeight),
            elevation = CardDefaults.cardElevation(defaultElevation = Dimens.defaultElevation)
        ) {}
        TinySpacer()
        Card(
            modifier = Modifier
                .height(MaterialTheme.typography.titleMedium.lineHeight.toDp())
                .fillMaxWidth(),
            shape = RoundedCornerShape(Dimens.tinyCornerRadius),
            elevation = CardDefaults.cardElevation(defaultElevation = Dimens.defaultElevation)
        ) {}
        TinySpacer()
        Card(
            modifier = Modifier
                .height(MaterialTheme.typography.titleMedium.lineHeight.toDp())
                .fillMaxWidth(0.5f),
            shape = RoundedCornerShape(Dimens.tinyCornerRadius),
            elevation = CardDefaults.cardElevation(defaultElevation = Dimens.defaultElevation)
        ) {}
    }
}
