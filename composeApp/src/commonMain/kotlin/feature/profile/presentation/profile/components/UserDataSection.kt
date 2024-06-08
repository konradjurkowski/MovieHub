package feature.profile.presentation.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import core.components.other.RegularSpacer
import core.components.user.UserAvatar

@Composable
fun UserDataSection(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        UserAvatar()
        RegularSpacer()
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = "Zomboy",
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = "konrad.jurkowski11@gmail.com",
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Light,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}
