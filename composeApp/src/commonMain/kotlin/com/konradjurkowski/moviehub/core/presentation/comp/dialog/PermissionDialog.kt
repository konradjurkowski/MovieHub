package com.konradjurkowski.moviehub.core.presentation.comp.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import com.konradjurkowski.moviehub.core.presentation.comp.other.RegularSpacer
import com.konradjurkowski.moviehub.core.presentation.comp.other.SmallSpacer
import com.konradjurkowski.moviehub.core.utils.Dimens
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.cancel
import moviehub.composeapp.generated.resources.go_to_settings
import moviehub.composeapp.generated.resources.permission_required
import org.jetbrains.compose.resources.stringResource

@Composable
fun PermissionDialog(
    visible: Boolean = false,
    message: String,
    onDismiss: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
) {
    if (!visible) return

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.background,
            shape = RoundedCornerShape(Dimens.radius10),
        ) {
            Column(modifier = Modifier.padding(Dimens.padding16)) {
                Text(
                    text = stringResource(Res.string.permission_required),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                RegularSpacer()
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                RegularSpacer()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(
                        content = { Text(text = stringResource(Res.string.cancel)) },
                        onClick = onDismiss,
                    )
                    SmallSpacer()
                    TextButton(
                        content = { Text(text = stringResource(Res.string.go_to_settings)) },
                        onClick = {
                            onDismiss()
                            onGoToAppSettingsClick()
                        },
                    )
                }
            }
        }
    }
}
