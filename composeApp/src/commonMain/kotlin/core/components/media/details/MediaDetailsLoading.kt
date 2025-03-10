package core.components.media.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.components.button.BoxButton
import core.components.loading.LoadingIndicator
import core.utils.Dimens

@Composable
fun MediaDetailsLoading(
    modifier: Modifier = Modifier,
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
        LoadingIndicator(modifier = Modifier.fillMaxSize().weight(1f))
    }
}
