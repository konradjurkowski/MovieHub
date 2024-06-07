package core.utils

import io.ktor.client.statement.HttpResponse
import kotlin.math.roundToInt

fun Double.round(decimals: Int): Double {
    var dotAt = 1
    repeat(decimals) { dotAt *= 10 }
    val roundedValue = (this * dotAt).roundToInt()
    return (roundedValue / dotAt) + (roundedValue % dotAt).toDouble() / dotAt
}

suspend inline fun <reified T> safeApiCall(
    call: () -> HttpResponse,
    crossinline handleResult: suspend (HttpResponse) -> Resource<T>
): Resource<T> {
    return try {
        val response = call()
        if (response.status.value in 200..299) {
            handleResult(response)
        } else {
            Resource.Failure(FailureResponseException())
        }
    } catch (e: Exception) {
        Resource.Failure(e)
    }
}
