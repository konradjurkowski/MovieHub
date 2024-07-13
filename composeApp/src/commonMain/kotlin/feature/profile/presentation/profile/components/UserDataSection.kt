package feature.profile.presentation.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import core.components.other.RegularSpacer
import core.components.user.UserAvatar
import dev.gitlive.firebase.auth.FirebaseUser

@Composable
fun UserDataSection(
    modifier: Modifier = Modifier,
    user: FirebaseUser,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        UserAvatar(imageUrl = user.photoURL)
        RegularSpacer()
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = user.displayName ?: "",
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = user.email ?: "",
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Light,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}
