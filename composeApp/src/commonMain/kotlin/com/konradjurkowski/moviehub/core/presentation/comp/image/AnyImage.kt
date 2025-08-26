package com.konradjurkowski.moviehub.core.presentation.comp.image

import androidx.compose.foundation.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import com.konradjurkowski.moviehub.core.presentation.theme.withA40
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
            AsyncImage(
                modifier = modifier,
                model = image,
                contentDescription = null,
                contentScale = contentScale,
                placeholder = ColorPainter(MaterialTheme.colorScheme.onBackground.withA40()),
                error = painterResource(placeholderRes),
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
