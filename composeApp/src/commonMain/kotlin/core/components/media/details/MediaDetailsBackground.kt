package core.components.media.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import core.components.image.AnyImage

@Composable
fun MediaDetailsBackground(
    modifier: Modifier = Modifier,
    backgroundPath: String?,
) {
    Box(modifier = modifier.fillMaxSize()) {
        AnyImage(
            modifier = Modifier.fillMaxSize(),
            image = backgroundPath,
        )
        Box(modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        MaterialTheme.colorScheme.background,
                    ),
                ),
            )
        )
    }
}
