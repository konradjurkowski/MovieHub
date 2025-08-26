package com.konradjurkowski.moviehub.feature.auth.data.api

import com.konradjurkowski.moviehub.feature.auth.domain.api.UserApi
import io.ktor.client.HttpClient
import io.ktor.client.request.request
import io.ktor.http.HttpMethod
import io.ktor.http.path

class UserApiImpl(
    private val httpClient: HttpClient,
) : UserApi {

    override suspend fun getUserDetails() = httpClient.request {
        method = HttpMethod.Get
        url { path("/api/user") }
    }
}
