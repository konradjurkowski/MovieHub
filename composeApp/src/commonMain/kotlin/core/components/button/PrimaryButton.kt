package core.components.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import core.components.loading.LoadingIndicator
import core.utils.Dimens
import core.utils.LocalTouchFeedback

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    loading: Boolean = false,
    verticalPadding: Dp = Dimens.padding8,
    horizontalPadding: Dp = Dimens.padding16,
    onClick: () -> Unit,
) {
    val touchFeedback = LocalTouchFeedback.current
    ElevatedButton(
        modifier = modifier
            .heightIn(Dimens.defaultButtonHeight),
        onClick = {
            touchFeedback.performLongPress()
            onClick()
        },
        contentPadding = PaddingValues(
            vertical = verticalPadding,
            horizontal = horizontalPadding
        ),
        enabled = enabled,
        shape = RoundedCornerShape(Dimens.radius12),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ),
    ) {
        if (loading) {
            LoadingIndicator(
                size = Dimens.buttonLoadingSize,
                color = MaterialTheme.colorScheme.onPrimary
            )
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        }
    }
}
