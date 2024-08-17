package feature.home.presentation.main.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.utils.Dimens
import core.utils.LocalTouchFeedback
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.ic_add
import org.jetbrains.compose.resources.painterResource

@Composable
fun AddButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val touchFeedback = LocalTouchFeedback.current

    IconButton(
        modifier = modifier,
        onClick = {
            touchFeedback.performLongPress()
            onClick()
        },
    ) {
        Icon(
            modifier = Modifier.size(Dimens.icon32),
            painter = painterResource(Res.drawable.ic_add),
            contentDescription = "Add icon",
            tint = MaterialTheme.colorScheme.primary,
        )
    }
}
