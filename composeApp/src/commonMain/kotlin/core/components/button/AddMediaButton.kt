package core.components.button

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import core.components.other.SmallSpacer
import core.utils.Dimens
import core.utils.LocalTouchFeedback

@Composable
fun AddMediaButton(
    modifier: Modifier = Modifier,
    addedTitle: String,
    notAddedTitle: String,
    isVisible: Boolean,
    isAdded: Boolean,
    onClick: () -> Unit = {},
) {
    val touchFeedback = LocalTouchFeedback.current

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { fullHeight -> fullHeight } ),
        exit = slideOutVertically(targetOffsetY = { fullHeight -> fullHeight } ),
    ) {
        Row(
            modifier = modifier
                .clickable(enabled = !isAdded) {
                    touchFeedback.performLongPress()
                    onClick()
                }
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .navigationBarsPadding()
                .padding(Dimens.padding16),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            if (isAdded) {
                Text(
                    text = addedTitle.uppercase(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Media Icon",
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
                SmallSpacer()
                Text(
                    text = notAddedTitle.uppercase(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    }
}
