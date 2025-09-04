package com.konradjurkowski.moviehub.core.presentation.comp.media.paging

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import com.konradjurkowski.moviehub.core.presentation.comp.button.AnimatedIconButton
import com.konradjurkowski.moviehub.core.presentation.comp.image.AnyImage
import com.konradjurkowski.moviehub.core.presentation.theme.withA20
import com.konradjurkowski.moviehub.core.utils.Dimens

@Composable
fun GridMediaPagingItem(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    isAdded: Boolean,
    onAddClick: () -> Unit,
    onCardClick: () -> Unit,
) {
    val hapticFeedback = LocalHapticFeedback.current
    val focusManager = LocalFocusManager.current

    Card(
        modifier = modifier
            .aspectRatio(3/4f)
            .padding(Dimens.padding8),
        onClick = {
            hapticFeedback.performHapticFeedback(HapticFeedbackType.ContextClick)
            focusManager.clearFocus()
            onCardClick()
        },
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.defaultElevation),
        shape = RoundedCornerShape(Dimens.radius8),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AnyImage(
                modifier = Modifier.fillMaxSize(),
                image = imageUrl,
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.withA20()),
            )
            AnimatedIconButton(
                modifier = Modifier
                    .padding(Dimens.padding8)
                    .align(Alignment.TopEnd),
                icon = Icons.Default.Add,
                isVisible = !isAdded,
                onClick = onAddClick,
            )
        }
    }
}
