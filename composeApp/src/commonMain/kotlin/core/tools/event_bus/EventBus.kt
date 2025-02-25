package core.tools.event_bus

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class EventBus {
    private val _events = MutableSharedFlow<AppEvent>()
    val events = _events.asSharedFlow()

    suspend fun invokeEvent(event: AppEvent) = _events.emit(event)
}

abstract class AppEvent
data class RefreshMovie(val movieId: Long) : AppEvent()
data class RefreshSeries(val seriesId: Long) : AppEvent()
