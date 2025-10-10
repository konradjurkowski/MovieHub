package com.konradjurkowski.moviehub.core.navigation

import com.konradjurkowski.moviehub.core.domain.model.navigation.NavAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

interface AppNavigator {
    val navActionFlow: SharedFlow<NavAction>
    fun back()
    fun <T : Any> backTo(route: T)
    fun <T : Any> push(route: T)
    fun <T : Any> replace(route: T)
    fun <T : Any> replaceAll(route: T)
    fun openUrl(url: String)
}

class AppNavigatorImpl : AppNavigator {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val _navActionFlow = MutableSharedFlow<NavAction>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    override val navActionFlow = _navActionFlow.asSharedFlow()

    override fun back() {
        scope.launch { _navActionFlow.emit(NavAction.Back) }
    }

    override fun <T : Any> backTo(route: T) {
        scope.launch { _navActionFlow.emit(NavAction.BackTo(route)) }
    }

    override fun <T : Any> push(route: T) {
        scope.launch { _navActionFlow.emit(NavAction.Push(route)) }
    }

    override fun <T : Any> replace(route: T) {
        scope.launch { _navActionFlow.emit(NavAction.Replace(route)) }
    }

    override fun <T : Any> replaceAll(route: T) {
        scope.launch { _navActionFlow.emit(NavAction.ReplaceAll(route)) }
    }

    override fun openUrl(url: String) {
        scope.launch { _navActionFlow.emit(NavAction.OpenUrl(url)) }
    }
}
