package feature.home.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import core.theme.movieGradientColors
import core.theme.seriesGradientColors
import core.utils.Dimens
import core.utils.LocalTouchFeedback
import core.utils.getScreenSizeInfo
import feature.home.presentation.draw.DrawType
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.home_screen_draw_movie
import moviehub.composeapp.generated.resources.home_screen_draw_series
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeDrawMediaButton(
    modifier: Modifier = Modifier,
    drawType: DrawType,
    onPressed: () -> Unit,
) {
    val screenSizeInfo = getScreenSizeInfo()
    val touchFeedback = LocalTouchFeedback.current

    val gradientColors = when (drawType) {
        DrawType.MOVIE -> movieGradientColors
        DrawType.SERIES -> seriesGradientColors
    }

    Card(
        modifier = modifier.height(screenSizeInfo.height * 0.2f),
        shape = RoundedCornerShape(Dimens.radius12),
        onClick = {
            touchFeedback.performMediumImpact()
            onPressed()
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.linearGradient(gradientColors)),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = when (drawType) {
                    DrawType.MOVIE -> stringResource(Res.string.home_screen_draw_movie)
                    DrawType.SERIES -> stringResource(Res.string.home_screen_draw_series)
                },
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}
