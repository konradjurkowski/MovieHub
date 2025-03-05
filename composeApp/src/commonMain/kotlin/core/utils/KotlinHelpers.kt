package core.utils

import core.model.Response
import core.utils.constants.Constants
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.withTimeout
import kotlin.math.roundToInt

fun Double.round(decimals: Int): Double {
    var dotAt = 1
    repeat(decimals) { dotAt *= 10 }
    val roundedValue = (this * dotAt).roundToInt()
    return (roundedValue / dotAt) + (roundedValue % dotAt).toDouble() / dotAt
}

suspend inline fun <reified T> safeApiCall(
    call: () -> HttpResponse,
    crossinline handleResult: suspend (HttpResponse) -> Response<T>,
): Response<T> {
    return try {
        val response = call()
        if (response.status.value in 200..299) {
            handleResult(response)
        } else {
            Response.Failure(FailureResponseException())
        }
    } catch (e: Exception) {
        Response.Failure(e)
    }
}

suspend fun <T> runWithTimeout(
    timeoutMillis: Long = Constants.DEFAULT_TIMEOUT_IN_MS,
    call: suspend () -> Response<T>,
): Response<T> {
    return try {
        withTimeout(timeoutMillis) { call() }
    } catch (e: Exception) {
        Response.Failure(e)
    }
}

fun isNotificationPermissionRequired(): Boolean {
    val platform = PlatformInfo.platform
    return platform == Platform.IOS || (platform == Platform.Android && PlatformInfo.sdkInt >= Constants.ANDROID_13_VERSION_CODE)
}
