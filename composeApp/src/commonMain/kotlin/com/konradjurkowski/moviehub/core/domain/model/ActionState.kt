package com.konradjurkowski.moviehub.core.domain.model

sealed interface ActionState {
    data object Idle : ActionState
    data object Loading : ActionState
    data object Failure : ActionState
    data object Success : ActionState

    fun isLoading() = this is Loading
    fun isSuccess() = this is Success
    fun isFailure() = this is Failure
}
