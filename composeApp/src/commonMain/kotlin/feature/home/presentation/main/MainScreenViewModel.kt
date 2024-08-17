package feature.home.presentation.main

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.tools.event_bus.EventBus
import core.tools.event_bus.SetNavigationTab
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val eventBus: EventBus
) : BaseViewModel<MainScreenIntent, MainScreenSideEffect, MainScreenState>() {

    init {
        screenModelScope.launch {
            eventBus.events
                .filterIsInstance<SetNavigationTab>()
                .collectLatest {
                    sendSideEffect(MainScreenSideEffect.SetTab(it.tab))
                }
        }
    }
    override fun getDefaultState() = MainScreenState()

    override fun processIntent(intent: MainScreenIntent) {
        when (intent) {
            MainScreenIntent.AddPressed -> sendSideEffect(MainScreenSideEffect.GoToAdd)
            is MainScreenIntent.TabPressed -> sendSideEffect(MainScreenSideEffect.SetTab(intent.tab))
        }
    }
}
