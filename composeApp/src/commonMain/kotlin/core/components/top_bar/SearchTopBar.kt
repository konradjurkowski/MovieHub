package core.components.top_bar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import core.components.text_field.InputTextField
import core.utils.LocalTouchFeedback
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.search_label
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun SearchTopBar(
    modifier: Modifier = Modifier,
    value: String,
    isButtonEnabled: Boolean = true,
    onValueChanged: (String) -> Unit,
    onSearchPressed: (String) -> Unit,
) {
    val touchFeedback = LocalTouchFeedback.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    TopAppBar(
        modifier = modifier,
        title = {
            InputTextField(
                modifier = Modifier.focusRequester(focusRequester),
                value = value,
                onValueChanged = onValueChanged,
                verticalPadding = 0.dp,
                imeAction = ImeAction.Search,
                onSearch = {
                    onSearchPressed(value)
                }
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
