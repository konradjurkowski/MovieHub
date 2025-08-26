package com.konradjurkowski.moviehub.feature.auth.domain.api

import io.ktor.client.statement.HttpResponse

interface AuthApi {
    suspend fun login(email: String, password: String) : HttpResponse
    suspend fun register(name: String, email: String, password: String) : HttpResponse
    suspend fun logout(refreshToken: String) : HttpResponse
}
