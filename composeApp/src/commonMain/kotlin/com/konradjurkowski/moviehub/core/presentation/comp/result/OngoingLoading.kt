package com.konradjurkowski.moviehub.core.presentation.comp.result

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.konradjurkowski.moviehub.core.utils.Dimens

@Composable
fun OngoingLoading(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier
            .height(Dimens.ongoingViewHeight)
            .fillMaxWidth()
            .padding(horizontal = Dimens.padding16)
            .wrapContentWidth(Alignment.CenterHorizontally),
    )
}
