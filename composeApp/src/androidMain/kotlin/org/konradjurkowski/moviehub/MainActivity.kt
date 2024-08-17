package org.konradjurkowski.moviehub

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.platform.LocalHapticFeedback
import core.utils.AndroidHapticFeedback

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App(
                isDarkTheme = isSystemInDarkTheme(),
                touchFeedback = AndroidHapticFeedback(LocalHapticFeedback.current),
            )
        }
    }
}
