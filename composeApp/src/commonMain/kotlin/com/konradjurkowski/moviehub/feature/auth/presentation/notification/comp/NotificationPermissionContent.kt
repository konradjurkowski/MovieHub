package com.konradjurkowski.moviehub.feature.auth.presentation.notification.comp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.konradjurkowski.moviehub.core.presentation.comp.button.PrimaryButton
import com.konradjurkowski.moviehub.core.presentation.comp.button.SecondaryButton
import com.konradjurkowski.moviehub.core.presentation.comp.other.MediumSpacer
import com.konradjurkowski.moviehub.core.presentation.comp.other.RegularSpacer
import com.konradjurkowski.moviehub.core.utils.Dimens
import com.konradjurkowski.moviehub.core.utils.helpers.rememberScreenSize
import com.konradjurkowski.moviehub.feature.auth.presentation.notification.ise.NotificationPermissionIntent
import com.konradjurkowski.moviehub.feature.auth.presentation.notification.ise.NotificationPermissionIntent.AllowPressed
import com.konradjurkowski.moviehub.feature.auth.presentation.notification.ise.NotificationPermissionIntent.DenyPressed
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.ic_notification_permission
import moviehub.composeapp.generated.resources.notification_permission_screen_enable_label
import moviehub.composeapp.generated.resources.notification_permission_screen_enable_notifications_message
import moviehub.composeapp.generated.resources.notification_permission_screen_enable_notifications_title
import moviehub.composeapp.generated.resources.notification_permission_screen_not_now_label
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun NotificationPermissionContent(
    onIntent: (NotificationPermissionIntent) -> Unit,
) {
    val screenSize = rememberScreenSize()

    Scaffold { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(Dimens.padding16),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Image(
                    modifier = Modifier.height(screenSize.height * 0.3f)
                        .aspectRatio(1f),
                    painter = painterResource(Res.drawable.ic_notification_permission),
                    contentDescription = null,
                )
                MediumSpacer()
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(Res.string.notification_permission_screen_enable_notifications_title),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )
                RegularSpacer()
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(Res.string.notification_permission_screen_enable_notifications_message),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium,
                )
            }
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.notification_permission_screen_enable_label),
                onClick = { onIntent(AllowPressed) },
            )
            RegularSpacer()
            SecondaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.notification_permission_screen_not_now_label),
                onClick = { onIntent(DenyPressed) },
            )
        }
    }
}
