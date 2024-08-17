package core.tools.event_bus

import cafe.adriel.voyager.navigator.tab.Tab
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class EventBus {
    private val _events = MutableSharedFlow<AppEvent>()
    val events = _events.asSharedFlow()

    suspend fun invokeEvent(event: AppEvent) = _events.emit(event)
}

abstract class AppEvent
data class SetNavigationTab(val tab: Tab) : AppEvent()
data object RefreshMovieList : AppEvent()
data class RefreshMovie(val movieId: Long) : AppEvent()
data object RefreshSeriesList : AppEvent()
data class RefreshSeries(val seriesId: Long) : AppEvent()
