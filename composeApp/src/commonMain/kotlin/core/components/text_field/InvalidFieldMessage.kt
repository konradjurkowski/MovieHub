package core.components.text_field

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import core.utils.Dimens

@Composable
fun InvalidFieldMessage(
    modifier: Modifier = Modifier,
    message: String,
    isInvalid: Boolean
) {
    AnimatedVisibility(modifier = modifier, visible = isInvalid) {
        Text(
            modifier = Modifier
                .padding(top = Dimens.padding4)
                .fillMaxWidth(),
            text = message,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Start,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}
