package com.konradjurkowski.moviehub.core.presentation.comp.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.konradjurkowski.moviehub.core.presentation.comp.other.SmallSpacer
import com.konradjurkowski.moviehub.core.utils.Dimens
import com.konradjurkowski.moviehub.core.utils.extensions.noRippleClickable
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.unable_to_load_data
import org.jetbrains.compose.resources.stringResource

@Composable
fun OngoingError(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .noRippleClickable(onClick = onClick)
            .height(Dimens.ongoingViewHeight)
            .fillMaxWidth()
            .padding(horizontal = Dimens.padding16),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
        )
        SmallSpacer()
        Text(text = stringResource(Res.string.unable_to_load_data))
    }
}
