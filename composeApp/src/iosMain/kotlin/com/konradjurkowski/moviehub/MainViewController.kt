package com.konradjurkowski.moviehub

import androidx.compose.ui.uikit.OnFocusBehavior
import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController(
    configure = { onFocusBehavior = OnFocusBehavior.FocusableAboveKeyboard },
    content = { App() },
)
