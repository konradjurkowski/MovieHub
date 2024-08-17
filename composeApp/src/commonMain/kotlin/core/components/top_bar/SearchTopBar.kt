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
import androidx.compose.ui.text.input.ImeAction
import core.components.text_field.InputTextField
import core.utils.Dimens
import core.utils.LocalTouchFeedback
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.search_label
import org.jetbrains.compose.resources.stringResource

@Composable
fun SearchTopBar(
    modifier: Modifier = Modifier,
    value: String,
    isButtonEnabled: Boolean = true,
    shouldRequestFocus: Boolean = true,
    onValueChange: (String) -> Unit,
    onSearchPressed: (String) -> Unit,
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
                imeAction = ImeAction.Search,
                onSearch = { onSearchPressed(value) },
                contentPadding = PaddingValues(Dimens.padding16, Dimens.padding8),
            )
        },
        actions = {
            TextButton(
                onClick = {
                    touchFeedback.performLongPress()
                    onSearchPressed(value)
                },
                enabled = isButtonEnabled,
            ) {
                Text(stringResource(Res.string.search_label))
            }
        },
        navigationIcon = {
            NavigateBackArrow()
        },
    )
}
