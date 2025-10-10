package com.konradjurkowski.moviehub.core.data.events

import com.konradjurkowski.moviehub.core.domain.events.EventBus
import com.konradjurkowski.moviehub.core.domain.model.EventBusEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class EventBusImpl : EventBus {

    private val _events = MutableSharedFlow<EventBusEvent>()
    override val events = _events.asSharedFlow()

    override suspend fun emit(event: EventBusEvent) = _events.emit(event)
}
