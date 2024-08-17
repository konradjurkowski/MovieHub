package feature.home.presentation.main

import cafe.adriel.voyager.navigator.tab.Tab
import core.architecture.MviIntent
import core.architecture.MviSideEffect
import core.architecture.MviState

@MviIntent
sealed class MainScreenIntent {
    data class TabPressed(val tab: Tab) : MainScreenIntent()
    data object AddPressed : MainScreenIntent()
}

@MviSideEffect
sealed class MainScreenSideEffect {
    data class SetTab(val tab: Tab) : MainScreenSideEffect()
    data object GoToAdd : MainScreenSideEffect()
}

@MviState
class MainScreenState
