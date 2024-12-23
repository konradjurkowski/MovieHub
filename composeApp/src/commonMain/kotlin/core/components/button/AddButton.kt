package core.components.button

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.utils.Dimens
import core.utils.LocalTouchFeedback

@Composable
fun AddButton(
    modifier: Modifier = Modifier,
    isVisible: Boolean = true,
    onClick: () -> Unit = {},
) {
    val touchFeedback = LocalTouchFeedback.current

    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible,
        enter = slideInHorizontally(initialOffsetX = { - (it * 2) } ),
        exit = slideOutHorizontally(targetOffsetX = { it * 2 } ),
    ) {
        OutlinedIconButton(
            modifier = Modifier.size(30.dp),
            shape = RoundedCornerShape(Dimens.padding4),
            enabled = isVisible,
            onClick = {
                touchFeedback.performLongPress()
                onClick()
            },
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.primary),
            colors = IconButtonDefaults.outlinedIconButtonColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.primary,
            ),
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Icon",
                tint = MaterialTheme.colorScheme.primary,
            )
        }
    }
}
