package com.konradjurkowski.moviehub.core.presentation.comp.text

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import com.konradjurkowski.moviehub.core.utils.Dimens
import com.konradjurkowski.moviehub.core.utils.extensions.click

private const val CLICKABLE_TAG = "clickable_tag"

@Composable
fun ClickableTextFooter(
    modifier: Modifier = Modifier,
    textPart1: String,
    textPart2: String,
    onClick: () -> Unit,
) {
    val hapticFeedback = LocalHapticFeedback.current
    val textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)

    val annotatedString = buildAnnotatedString {
        withStyle(style = textStyle.copy(color = MaterialTheme.colorScheme.onBackground).toSpanStyle()) {
            append("$textPart1 ")
        }
        pushStringAnnotation(tag = CLICKABLE_TAG, annotation = textPart2)
        withStyle(style = textStyle.copy(color = MaterialTheme.colorScheme.primary).toSpanStyle()) {
            append(textPart2)
        }
        pop()
    }

    ClickableText(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(vertical = Dimens.padding8, horizontal = Dimens.padding16),
        text = annotatedString,
        style = textStyle.copy(textAlign = TextAlign.Center)
    ) { offset ->
        annotatedString.getStringAnnotations(CLICKABLE_TAG, offset, offset).firstOrNull()?.let {
            hapticFeedback.click()
            onClick()
        }
    }
}
