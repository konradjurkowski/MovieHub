package com.konradjurkowski.moviehub.core.presentation.comp.top_bar

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.konradjurkowski.moviehub.core.presentation.comp.text_field.InputTextField
import com.konradjurkowski.moviehub.core.utils.Dimens
import com.konradjurkowski.moviehub.core.utils.extensions.click
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.clear_label
import org.jetbrains.compose.resources.stringResource

@Composable
fun SearchTopBar(
    modifier: Modifier = Modifier,
    value: String,
    requestFocus: Boolean = false,
    onValueChange: (String) -> Unit,
    onClearPressed: () -> Unit,
) {
    val hapticFeedback = LocalHapticFeedback.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        if (requestFocus) focusRequester.requestFocus()
    }

    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults
            .topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
        navigationIcon = {
            NavigateBackArrow()
        },
        title = {
            InputTextField(
                modifier = Modifier
                    .height(40.dp)
                    .focusRequester(focusRequester),
                value = value,
                onValueChange = onValueChange,
                textStyle = MaterialTheme.typography.bodySmall,
                contentPadding = PaddingValues(Dimens.padding16, Dimens.padding2),
            )
        },
        actions = {
            TextButton(
                onClick = {
                    hapticFeedback.click()
                    onClearPressed()
                },
                enabled = value.isNotEmpty(),
            ) {
                Text(stringResource(Res.string.clear_label))
            }
        },
    )
}
