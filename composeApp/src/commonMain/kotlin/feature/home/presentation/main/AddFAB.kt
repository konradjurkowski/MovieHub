package feature.home.presentation.main

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import core.utils.Dimens
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.ic_add
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AddFAB(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val hapticFeedback = LocalHapticFeedback.current
    FloatingActionButton(
        modifier = modifier
            .offset(y = -Dimens.regularPadding),
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        onClick = {
            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
            onClick()
        },
    ) {
        Icon(
            modifier = Modifier.size(Dimens.mediumIconSize),
            painter = painterResource(Res.drawable.ic_add),
            contentDescription = "Add FAB",
        )
    }
}
