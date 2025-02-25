package org.konradjurkowski.moviehub

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalHapticFeedback
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration
import core.tools.haptic.AndroidHapticFeedback
import core.utils.LocalTouchFeedback

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NotifierManager
            .initialize(configuration = NotificationPlatformConfiguration.Android(notificationIconResId = R.drawable.ic_notification))

        enableEdgeToEdge()
        setContent {
            CompositionLocalProvider(
                LocalTouchFeedback provides AndroidHapticFeedback(LocalHapticFeedback.current),
            ) {
                App()
            }
        }
    }
}
