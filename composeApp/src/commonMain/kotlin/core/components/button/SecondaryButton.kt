package core.components.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import core.utils.Dimens
import core.utils.LocalTouchFeedback

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    text: String,
    verticalPadding: Dp = Dimens.padding8,
    horizontalPadding: Dp = Dimens.padding16,
    onClick: () -> Unit,
) {
    val touchFeedback = LocalTouchFeedback.current
    OutlinedButton(
        modifier = modifier.heightIn(Dimens.defaultButtonHeight),
        onClick = {
            touchFeedback.performLongPress()
            onClick()
        },
        contentPadding = PaddingValues(
            vertical = verticalPadding,
            horizontal = horizontalPadding,
        ),
        shape = RoundedCornerShape(Dimens.radius12),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
    }
}
