package com.konradjurkowski.moviehub.core.presentation.comp.text_field

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.konradjurkowski.moviehub.core.utils.Dimens

@Composable
fun InputTextField(
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
    keyboardType: KeyboardType = KeyboardType.Text,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    imeAction: ImeAction = ImeAction.Done,
    contentPadding: PaddingValues = PaddingValues(Dimens.padding16),
    onDone: () -> Unit = {},
    onSearch: () -> Unit = {},
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    val containerColor = MaterialTheme.colorScheme.onBackground.copy(0.05f)
    val borderColor = MaterialTheme.colorScheme.onBackground.copy(0.2f)

    val visualTransformation =  if (obscure) PasswordVisualTransformation() else VisualTransformation.None
    val focusManager = LocalFocusManager.current

    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 60.dp),
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        readOnly = readOnly,
        singleLine = singleLine,
        maxLines = 1,
        interactionSource = interactionSource,
        visualTransformation = visualTransformation,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        textStyle = textStyle.copy(color = MaterialTheme.colorScheme.onBackground),
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
            keyboardType = keyboardType,
            capitalization = capitalization,
            autoCorrect = false,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                onDone()
            },
            onSearch = {
                focusManager.clearFocus()
                onSearch()
            }
        ),
    ) { innerTextField ->
        OutlinedTextFieldDefaults.DecorationBox(
            value = value,
            innerTextField = innerTextField,
            enabled = enabled,
            singleLine = singleLine,
            interactionSource = interactionSource,
            visualTransformation = visualTransformation,
            contentPadding = contentPadding,
            trailingIcon = trailingIcon,
            leadingIcon = leadingIcon,
            placeholder = { Text(text = "", style = textStyle) },
            container = {
                OutlinedTextFieldDefaults.ContainerBox(
                    enabled = enabled,
                    isError = isError,
                    interactionSource = interactionSource,
                    shape = RoundedCornerShape(Dimens.radius8),
                    colors = OutlinedTextFieldDefaults.colors(
                        errorContainerColor = containerColor,
                        focusedContainerColor = containerColor,
                        unfocusedContainerColor = containerColor,
                        focusedBorderColor = borderColor,
                        unfocusedBorderColor = Color.Transparent,
                    ),
                )
            },
        )
    }
}
