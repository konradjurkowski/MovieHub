package core.components.image

import androidx.compose.foundation.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.placeholder
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun AnyImage(
    modifier: Modifier = Modifier,
    image: Any? = null,
    contentScale: ContentScale = ContentScale.Crop,
    placeholderRes: DrawableResource = Res.drawable.placeholder,
) {
    when (image) {
        is String -> {
            KamelImage(
                modifier = modifier,
                resource = asyncPainterResource(data = image),
                contentDescription = null,
                contentScale = contentScale,
                onLoading = {
                    Surface(modifier, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)) { }
                },
                onFailure = { ImagePlaceholder(modifier, contentScale, placeholderRes) }
            )
        }

        is ImageBitmap -> {
            Image(
                modifier = modifier,
                bitmap = image,
                contentScale = contentScale,
                contentDescription = null,
            )
        }

        else -> ImagePlaceholder(modifier, contentScale, placeholderRes)
    }
}

@Composable
private fun ImagePlaceholder(
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
