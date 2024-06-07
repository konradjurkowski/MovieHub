package core.utils

import androidx.compose.runtime.Composable

sealed class Resource<out T> {
    data object Idle : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Failure(val error: Throwable) : Resource<Nothing>()

    fun isLoading() = this is Loading
    fun isSuccess() = this is Success
    fun isFailure() = this is Failure

    /**
     * Returns data from a [Success].
     * @throws ClassCastException If the current state is not [Success]
     *  */
    fun getSuccess(): T? {
        if (this is Success) return data
        return null
    }

    /**
     * Returns an error message from an [Failure]
     * @throws ClassCastException If the current state is not [Failure]
     *  */
    fun getFailure(): Throwable? {
        if (this is Failure) return error
        return null
    }

    @Composable
    fun displayResult(
        onSuccess: @Composable (T) -> Unit,
        onFailure: @Composable (Throwable) -> Unit,
        onLoading: @Composable () -> Unit,
    ) {
        when (this) {
            is Success -> onSuccess(data)
            is Failure -> onFailure(error)
            Loading, Idle -> onLoading()
        }
    }
}
