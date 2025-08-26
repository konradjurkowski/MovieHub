package com.konradjurkowski.moviehub.core.presentation.comp.text_field

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.konradjurkowski.moviehub.core.presentation.theme.withA40
import com.konradjurkowski.moviehub.core.utils.Dimens
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.ic_visibility
import moviehub.composeapp.generated.resources.ic_visibility_off
import org.jetbrains.compose.resources.painterResource

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    obscure: Boolean = false,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    isError: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    readOnly: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Password,
    imeAction: ImeAction = ImeAction.Done,
    contentPadding: PaddingValues = PaddingValues(Dimens.padding16),
    onSuffixIconClick: () -> Unit,
) {
    InputTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle,
        obscure = obscure,
        enabled = enabled,
        singleLine = singleLine,
        isError = isError,
        interactionSource = interactionSource,
        readOnly = readOnly,
        keyboardType = keyboardType,
        imeAction = imeAction,
        contentPadding = contentPadding,
        trailingIcon = {
            IconButton(onClick = onSuffixIconClick) {
                val visibilityIcon = when (obscure) {
                    true -> Res.drawable.ic_visibility
                    false -> Res.drawable.ic_visibility_off
                }
                Icon(
                    painter = painterResource(visibilityIcon),
                    tint = MaterialTheme.colorScheme.onBackground.withA40(),
                    contentDescription = "visibility icon",
                )
            }
        }
    )
}
