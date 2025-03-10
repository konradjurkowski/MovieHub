package core.model

sealed interface ActionState {
    data object Idle : ActionState
    data object Loading : ActionState
    data class Failure(val error: Throwable) : ActionState
    data object Success : ActionState

    fun isLoading() = this is Loading
    fun isSuccess() = this is Success
    fun isFailure() = this is Failure
}
