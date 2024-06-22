package core.components.text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.components.other.TinySpacer

@Composable
fun AuthFooter(
    modifier: Modifier = Modifier,
    textPart1: String,
    textPart2: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = textPart1,
            style = MaterialTheme.typography.bodyLarge,
        )
        TinySpacer()
        InteractiveText(
            text = textPart2,
            onClick = onClick
        )
    }
}
