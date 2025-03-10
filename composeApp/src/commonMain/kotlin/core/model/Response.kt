package core.model

sealed class Response<T> {
    data class Success<T>(val data: T) : Response<T>()
    data class Failure<T>(val error: Throwable) : Response<T>()

    fun isSuccess() = this is Success
    fun isFailure() = this is Failure

    fun getSuccess(): T? {
        if (this is Success) return data
        return null
    }

    fun getFailure(): Throwable? {
        if (this is Failure) return error
        return null
    }
}
