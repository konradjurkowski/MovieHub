package feature.home.presentation.main

import core.architecture.BaseViewModel

class MainScreenViewModel: BaseViewModel<MainScreenIntent, MainScreenSideEffect, MainScreenState>() {

    override fun getDefaultState() = MainScreenState()

    override fun processIntent(intent: MainScreenIntent) {
        when (intent) {
            is MainScreenIntent.TabPressed -> sendSideEffect(MainScreenSideEffect.SetTab(intent.tab))
        }
    }
}
