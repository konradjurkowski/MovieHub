package com.konradjurkowski.moviehub.core.utils

import com.konradjurkowski.moviehub.core.domain.model.Response
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess

suspend inline fun <reified T> safeApiCall(
    apiCall: () -> HttpResponse,
    crossinline mapper: suspend (HttpResponse) -> T,
): Response<T> {
    return runCatching { apiCall() }.fold(
        onSuccess = { response ->
            if (response.status.isSuccess()) return@fold Response.Success(mapper(response))
            Response.Failure(FailureResponseException())
        },
        onFailure = { exception ->
            Response.Failure(exception)
        },
    )
}
