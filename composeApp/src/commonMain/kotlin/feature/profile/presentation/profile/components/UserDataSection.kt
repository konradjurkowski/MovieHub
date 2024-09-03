package feature.profile.presentation.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import core.components.other.SmallSpacer
import core.components.other.TinySpacer
import core.components.user.UserAvatar
import feature.auth.domain.AppUser

@Composable
fun UserDataSection(
    modifier: Modifier = Modifier,
    user: AppUser? = null,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        UserAvatar(imageUrl = user?.imageUrl)
        SmallSpacer()
        Text(
            text = user?.name ?: "",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold,
        )
        TinySpacer()
        Text(
            text = user?.description ?: "",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.ExtraLight,
        )
    }
}
