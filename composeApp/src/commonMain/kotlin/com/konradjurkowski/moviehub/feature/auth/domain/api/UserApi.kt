package com.konradjurkowski.moviehub.feature.auth.domain.api

import io.ktor.client.statement.HttpResponse

interface UserApi {
    suspend fun getUserDetails(): HttpResponse
}
