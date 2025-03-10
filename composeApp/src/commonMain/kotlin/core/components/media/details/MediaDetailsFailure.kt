package core.components.media.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.components.button.BoxButton
import core.components.result.FailureWidget
import core.utils.Dimens

@Composable
fun MediaDetailsFailure(
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit,
    onBackPressed: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.padding16),
    ) {
        BoxButton(
            modifier = Modifier.safeDrawingPadding(),
            onClick = onBackPressed,
        )
        FailureWidget(
            modifier = Modifier.weight(1f),
            onButtonClick = onRefresh,
        )
    }
}
