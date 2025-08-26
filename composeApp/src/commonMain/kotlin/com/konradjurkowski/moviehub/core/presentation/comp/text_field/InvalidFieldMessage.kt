package com.konradjurkowski.moviehub.core.presentation.comp.text_field

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.konradjurkowski.moviehub.core.domain.model.ValidationResult
import com.konradjurkowski.moviehub.core.domain.model.toDisplay
import com.konradjurkowski.moviehub.core.utils.Dimens

@Composable
fun InvalidFieldMessage(
    modifier: Modifier = Modifier,
    result: ValidationResult,
) {
    AnimatedVisibility(modifier = modifier, visible = !result.successful) {
        Text(
            modifier = Modifier
                .padding(top = Dimens.padding4)
                .fillMaxWidth(),
            text = result.toDisplay(),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Start,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
