package core.components.image

import androidx.compose.foundation.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.placeholder
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun NetworkImage(
    modifier: Modifier = Modifier,
    imageUrl: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
    placeholderRes: DrawableResource = Res.drawable.placeholder,
) {
    imageUrl?.let {
        KamelImage(
            modifier = modifier,
            resource = asyncPainterResource(data = imageUrl),
            contentDescription = null,
            contentScale = contentScale,
            onLoading = {
                Surface(modifier, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)) { }
            },
            onFailure = { ImageItemPlaceholder(modifier, contentScale, placeholderRes) }
        )
    } ?: ImageItemPlaceholder(modifier, contentScale, placeholderRes)
}

@Composable
private fun ImageItemPlaceholder(
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    placeholderRes: DrawableResource = Res.drawable.placeholder,
) {
    Image(
        modifier = modifier,
        painter = painterResource(placeholderRes),
        contentScale = contentScale,
        contentDescription = null,
    )
}
