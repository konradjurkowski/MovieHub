package core.components.other

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.utils.Dimens

@Composable
fun TinySpacer() {
    Spacer(modifier = Modifier.size(Dimens.padding4))
}

@Composable
fun SmallSpacer() {
    Spacer(modifier = Modifier.size(Dimens.padding8))
}

@Composable
fun RegularSpacer() {
    Spacer(modifier = Modifier.size(Dimens.padding16))
}

@Composable
fun MediumSpacer() {
    Spacer(modifier = Modifier.size(Dimens.padding24))
}
