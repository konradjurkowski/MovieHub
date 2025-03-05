package core.model

sealed interface DisplayState<T> {
    data object Idle : DisplayState<Nothing>
    data object Loading : DisplayState<Nothing>
    data class Error(val error: Throwable) : DisplayState<Nothing>
    data class Success<T>(val data: T) : DisplayState<T>
}
