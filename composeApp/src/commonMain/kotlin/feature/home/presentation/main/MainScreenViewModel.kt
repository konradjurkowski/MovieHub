package feature.home.presentation.main

import core.architecture.BaseViewModel
import feature.home.presentation.main.MainScreenIntent.TabPressed
import feature.home.presentation.main.MainScreenSideEffect.SetTab

class MainScreenViewModel: BaseViewModel<MainScreenIntent, MainScreenSideEffect, MainScreenState>() {

    override fun getDefaultState() = MainScreenState()

    override fun processIntent(intent: MainScreenIntent) {
        when (intent) {
            is TabPressed -> sendSideEffect(SetTab(intent.tab))
        }
    }
}
