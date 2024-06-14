package core.components.text_field

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import core.utils.Dimens

@Composable
fun InputTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit,
    isError: Boolean = false,
    obscure: Boolean = false,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    onDone: () -> Unit = {},
    onSearch: () -> Unit = {},
    verticalPadding: Dp = Dimens.smallPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    val containerColor = MaterialTheme.colorScheme.onBackground.copy(0.05f)
    val borderColor = MaterialTheme.colorScheme.onBackground.copy(0.2f)

    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        modifier = modifier
            // To avoid cursor jump on iOS app
            .heightIn(Dimens.defaultTextFieldHeight)
            .fillMaxWidth()
            .padding(vertical = verticalPadding),
        value = value,
        onValueChange = onValueChanged,
        textStyle = MaterialTheme.typography.bodyLarge,
        shape = RoundedCornerShape(Dimens.smallCornerRadius),
        singleLine = true,
        isError = isError,
        keyboardOptions = KeyboardOptions(imeAction = imeAction, keyboardType = keyboardType),
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
        trailingIcon = trailingIcon,
        leadingIcon = leadingIcon,
        visualTransformation = if (obscure) PasswordVisualTransformation() else VisualTransformation.None,
        enabled = enabled,
        readOnly = readOnly,
        interactionSource = interactionSource,
        colors = OutlinedTextFieldDefaults.colors(
            errorContainerColor = containerColor,
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            focusedBorderColor = borderColor,
            unfocusedBorderColor = Color.Transparent,
        )
    )
}
