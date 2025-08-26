package com.konradjurkowski.moviehub.feature.auth.data.api

import com.konradjurkowski.moviehub.feature.auth.data.api.dto.request.LoginRequest
import com.konradjurkowski.moviehub.feature.auth.data.api.dto.request.LogoutRequest
import com.konradjurkowski.moviehub.feature.auth.data.api.dto.request.RegisterRequest
import com.konradjurkowski.moviehub.feature.auth.domain.api.AuthApi
import io.ktor.client.HttpClient
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import io.ktor.http.path

class AuthApiImpl(
    private val httpClient: HttpClient,
) : AuthApi {

    override suspend fun login(email: String, password: String) = httpClient.request {
        val request = LoginRequest(email = email, password = password)
        method = HttpMethod.Post
        url { path("/api/auth/login") }
        setBody(request)
    }

    override suspend fun register(name: String, email: String, password: String) = httpClient.request {
        val request = RegisterRequest(name = name, email = email, password = password)
        method = HttpMethod.Post
        url { path("/api/auth/register") }
        setBody(request)
    }

    override suspend fun logout(refreshToken: String) = httpClient.request {
        val request = LogoutRequest(refreshToken = refreshToken)
        method = HttpMethod.Post
        url { path("/api/auth/logout") }
        setBody(request)
    }
}
