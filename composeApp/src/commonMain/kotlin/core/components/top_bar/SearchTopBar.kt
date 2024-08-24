package core.components.top_bar

import androidx.compose.foundation.layout.PaddingValues
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
import core.components.text_field.InputTextField
import core.utils.Dimens
import core.utils.LocalTouchFeedback
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.clear_label
import org.jetbrains.compose.resources.stringResource

@Composable
fun SearchTopBar(
    modifier: Modifier = Modifier,
    value: String,
    shouldRequestFocus: Boolean = true,
    onValueChange: (String) -> Unit,
    onClearPressed: () -> Unit,
) {
    val touchFeedback = LocalTouchFeedback.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        if (shouldRequestFocus) focusRequester.requestFocus()
    }

    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        title = {
            InputTextField(
                modifier = Modifier.focusRequester(focusRequester),
                value = value,
                onValueChange = onValueChange,
                textStyle = MaterialTheme.typography.bodySmall,
                contentPadding = PaddingValues(Dimens.padding16, Dimens.padding8),
            )
        },
        actions = {
            TextButton(
                onClick = {
                    touchFeedback.performLongPress()
                    onClearPressed()
                },
                enabled = value.isNotEmpty(),
            ) {
                Text(stringResource(Res.string.clear_label))
            }
        },
        navigationIcon = {
            NavigateBackArrow()
        },
    )
}
