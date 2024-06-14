package core.components.result

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import core.utils.Dimens

@Composable
fun OngoingLoading(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(
        modifier = modifier
            .height(Dimens.ongoingViewHeight)
            .fillMaxWidth()
            .padding(horizontal = Dimens.regularPadding)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}
