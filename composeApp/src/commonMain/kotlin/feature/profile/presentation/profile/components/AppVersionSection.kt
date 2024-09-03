package feature.profile.presentation.profile.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.konradjurkowski.moviehub.BuildKonfig
import core.theme.withA50
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.profile_screen_version_label
import org.jetbrains.compose.resources.stringResource

@Composable
fun AppVersionSection(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = "${stringResource(Res.string.profile_screen_version_label)} ${BuildKonfig.VERSION_NAME}",
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onBackground.withA50(),
    )
}
