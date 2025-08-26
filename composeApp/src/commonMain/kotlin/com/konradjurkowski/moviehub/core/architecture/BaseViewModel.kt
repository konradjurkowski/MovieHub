package com.konradjurkowski.moviehub.core.architecture

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val BUFFER_CAPACITY = 64
const val EVENTS_KEY = "EVENTS_KEY"

abstract class BaseViewModel<ViewIntent, ViewState, ViewEvent>(
    initialState: ViewState,
) : ViewModel() {

    protected abstract fun processIntent(intent: ViewIntent)

    protected val _viewState by lazy { MutableStateFlow(initialState) }
    val viewState = _viewState.asStateFlow()
    protected val state = _viewState.value

    private val _viewEvents = Channel<ViewEvent>(BUFFER_CAPACITY)
    val viewEvents = _viewEvents.receiveAsFlow()

    private val _viewIntents = MutableSharedFlow<ViewIntent>(extraBufferCapacity = BUFFER_CAPACITY)

    init {
        viewModelScope.launch {
            _viewIntents.collect { intent -> processIntent(intent) }
        }
    }

    protected open fun updateState(reducer: ViewState.() -> ViewState) {
        _viewState.update { viewState.value.reducer() }
    }

    protected fun sendEvent(event: ViewEvent) {
        viewModelScope.launch {
            _viewEvents.send(event)
        }
    }

    fun sendIntent(action: ViewIntent) {
        _viewIntents.tryEmit(action)
    }
}

inline fun <reified T> MutableStateFlow<in T>.transformIf(noinline transform: T.() -> T) {
    if (value !is T) return
    value = transform(value as T)
}

inline fun <reified T> MutableStateFlow<in T>.invokeIf(noinline action: T.() -> Unit) {
    if (value !is T) return
    action(value as T)
}

@Composable
fun <T> CollectEvents(eventsFlow: Flow<T>, onCollected: (T) -> Unit) {
    LaunchedEffect(EVENTS_KEY) {
        eventsFlow.collect { event ->
            onCollected(event)
        }
    }
}
