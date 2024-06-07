package core.components.other

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.utils.Dimens

@Composable
fun TinySpacer() {
    Spacer(modifier = Modifier.size(Dimens.tinyPadding))
}

@Composable
fun SmallSpacer() {
    Spacer(modifier = Modifier.size(Dimens.smallPadding))
}

@Composable
fun RegularSpacer() {
    Spacer(modifier = Modifier.size(Dimens.regularPadding))
}

@Composable
fun MediumSpacer() {
    Spacer(modifier = Modifier.size(Dimens.mediumPadding))
}
