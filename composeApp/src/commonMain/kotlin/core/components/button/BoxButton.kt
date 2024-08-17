package core.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import core.theme.withA50
import core.utils.Dimens
import core.utils.LocalTouchFeedback

@Composable
fun BoxButton(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.AutoMirrored.Default.ArrowBack,
    onClick: () -> Unit,
) {
    val touchFeedback = LocalTouchFeedback.current
    Surface(
        modifier = modifier
            .size(Dimens.icon48)
            .clip(RoundedCornerShape(Dimens.radius16))
            .clickable {
                touchFeedback.performLongPress()
                onClick()
            },
        color = MaterialTheme.colorScheme.background.withA50(),
        shape = RoundedCornerShape(Dimens.radius16),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onBackground),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.padding8),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "BoxButton icon",
            )
        }
    }
}
