package com.konradjurkowski.moviehub.core.utils.helpers

import com.konradjurkowski.moviehub.core.data.api.dto.ApiError
import com.konradjurkowski.moviehub.core.domain.model.Response
import com.konradjurkowski.moviehub.core.utils.exceptions.ApiException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess

suspend inline fun <reified T> safeApiCall(
    apiCall: () -> HttpResponse,
    crossinline mapper: suspend (HttpResponse) -> T,
): Response<T> {
    return try {
        val httpResponse = apiCall()
        if (httpResponse.status.isSuccess()) {
            return Response.Success(mapper(httpResponse))
        }

        val apiError = httpResponse.body<ApiError>()
        Response.Failure(ApiException(apiError.code.labelRes))
    } catch (e: Exception) {
        Response.Failure(e)
    }
}
