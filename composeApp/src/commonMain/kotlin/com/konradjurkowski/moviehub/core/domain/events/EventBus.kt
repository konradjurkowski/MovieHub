package com.konradjurkowski.moviehub.core.domain.events

import com.konradjurkowski.moviehub.core.domain.model.EventBusEvent
import kotlinx.coroutines.flow.SharedFlow

interface EventBus {
    val events: SharedFlow<EventBusEvent>
    suspend fun emit(event: EventBusEvent)
}
