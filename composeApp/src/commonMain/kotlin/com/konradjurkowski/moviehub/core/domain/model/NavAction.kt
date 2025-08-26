package com.konradjurkowski.moviehub.core.domain.model

sealed class NavAction {
    object Back : NavAction()
    data class BackTo<T : Any>(val route: T) : NavAction()
    data class Push<T : Any>(val route: T) : NavAction()
    data class Replace<T : Any>(val route: T) : NavAction()
    data class ReplaceAll<T : Any>(val route: T) : NavAction()
}
