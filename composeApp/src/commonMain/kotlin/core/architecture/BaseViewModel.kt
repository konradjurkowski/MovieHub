package core.architecture

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val BUFFER_CAPACITY = 64
const val SIDE_EFFECTS_KEY = "SIDE_EFFECTS_KEY"

abstract class BaseViewModel<ViewIntent, ViewSideEffect, ViewState> : ScreenModel {

    protected abstract fun getDefaultState(): ViewState
    protected abstract fun processIntent(intent: ViewIntent)

    private val _viewState by lazy { MutableStateFlow(getDefaultState()) }
    val viewState = _viewState.asStateFlow()

    private val _viewSideEffects = Channel<ViewSideEffect>(BUFFER_CAPACITY)
    val viewSideEffects = _viewSideEffects.receiveAsFlow()

    private val _viewIntents = MutableSharedFlow<ViewIntent>(extraBufferCapacity = BUFFER_CAPACITY)

    init {
        screenModelScope.launch {
            _viewIntents.collect { intent ->
                processIntent(intent)
            }
        }
    }

    protected open fun updateViewState(reducer: ViewState.() -> ViewState) {
        _viewState.update { viewState.value.reducer() }
    }

    protected fun sendSideEffect(sideEffect: ViewSideEffect) {
        screenModelScope.launch {
            _viewSideEffects.send(sideEffect)
        }
    }

    fun sendIntent(action: ViewIntent) {
        _viewIntents.tryEmit(action)
    }
}

@Composable
fun <T> CollectSideEffects(sideEffectsFlow: Flow<T>, onCollected: (T) -> Unit) {
    LaunchedEffect(SIDE_EFFECTS_KEY) {
        sideEffectsFlow.collect { effect ->
            onCollected(effect)
        }
    }
}
