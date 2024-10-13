package core.components.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import core.components.button.PrimaryButton
import core.components.other.RegularSpacer
import core.utils.Dimens
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.permission_confirm_button
import moviehub.composeapp.generated.resources.permission_gallery_permanently_denied
import org.jetbrains.compose.resources.stringResource

@Composable
fun PermissionDialog(
    modifier: Modifier = Modifier,
    message: String,
    onDismiss: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.background,
            shape = RoundedCornerShape(Dimens.radius10),
        ) {
            Column(modifier = modifier.padding(Dimens.padding16)) {
                Text(
                    text = stringResource(Res.string.permission_gallery_permanently_denied),
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
                PrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(Res.string.permission_confirm_button),
                    onClick = {
                        onDismiss()
                        onGoToAppSettingsClick()
                    },
                )
            }
        }
    }
}
