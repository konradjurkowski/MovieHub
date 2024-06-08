package core.components.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import core.utils.Dimens

@Composable
fun UserAvatar(
    modifier: Modifier = Modifier,
    size: Dp = Dimens.largeUserAvatar,
) {
    Box(
        modifier = modifier
            .size(size)
            .background(
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Z",
            fontSize = with(LocalDensity.current) { size.toSp() } * 0.6f,
            fontWeight = FontWeight.Bold,
        )
    }
}
