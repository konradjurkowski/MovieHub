package core.components.text

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import core.components.other.SmallSpacer
import core.utils.Dimens
import core.utils.LocalTouchFeedback

@Composable
fun SectionTitle(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit,
) {
    val touchFeedback = LocalTouchFeedback.current
    Row(
        modifier = modifier
            .padding(horizontal = Dimens.regularPadding)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f),
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        SmallSpacer()
        OutlinedButton(
            modifier = Modifier
                .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
            onClick = {
                touchFeedback.performLongPress()
                onClick()
            },
            contentPadding = PaddingValues(
                vertical = Dimens.tinyPadding,
                horizontal = Dimens.smallPadding
            ),
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.secondary),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.secondary,
            ),
        ) {
            Text("See more")
        }
    }
}
