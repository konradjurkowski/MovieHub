package core.components.result

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import core.components.other.RegularSpacer
import core.components.other.SmallSpacer
import core.theme.withA40
import core.utils.Dimens
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.empty_view_message
import moviehub.composeapp.generated.resources.empty_view_title
import moviehub.composeapp.generated.resources.ic_empty
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun EmptyView(
    modifier: Modifier = Modifier,
    title: String = stringResource(Res.string.empty_view_title),
    message: String = stringResource(Res.string.empty_view_message),
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimens.padding16),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(Res.drawable.ic_empty),
            contentDescription = "Empty Icon",
        )
        RegularSpacer()
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        SmallSpacer()
        Text(
            text = message,
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground.withA40(),
        )
    }
}
