package com.konradjurkowski.moviehub.core.presentation.comp.media.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.konradjurkowski.moviehub.core.presentation.comp.button.BoxButton
import com.konradjurkowski.moviehub.core.presentation.comp.result.FailureWidget
import com.konradjurkowski.moviehub.core.utils.Dimens

@Composable
fun MediaDetailsError(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onRefreshClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.padding16),
    ) {
        BoxButton(
            modifier = Modifier.safeDrawingPadding(),
            onClick = onBackClick,
        )
        FailureWidget(
            modifier = Modifier.weight(1f),
            onButtonClick = onRefreshClick,
        )
    }
}
