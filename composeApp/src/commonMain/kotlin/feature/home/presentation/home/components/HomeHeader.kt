package feature.home.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import core.components.other.SmallSpacer
import core.components.other.TinySpacer
import core.components.user.UserAvatar
import core.utils.Dimens
import feature.auth.domain.AppUser

@Composable
fun HomeHeader(
    user: AppUser
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .safeDrawingPadding()
            .padding(top = Dimens.padding16)
            .padding(horizontal = Dimens.padding16),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            val textStyle = MaterialTheme.typography.titleLarge
            Text(
                text = buildAnnotatedString {
                    withStyle(textStyle.copy(fontWeight = FontWeight.ExtraBold).toSpanStyle()) {
                        append("Hello, ")
                    }
                    append("${user.name}!")
                },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = textStyle,
            )
            TinySpacer()
            Text(
                text = "Check for latest addition.",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
        }
        SmallSpacer()
        UserAvatar(
            size = Dimens.icon48,
            imageUrl = user.imageUrl,
        )
    }
}
