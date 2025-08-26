package com.konradjurkowski.moviehub.feature.group.domain.api

import io.ktor.client.statement.HttpResponse

interface GroupApi {
    suspend fun createGroup(
        name: String,
        description: String = "",
        imageUrl: String? = null,
    ): HttpResponse
    suspend fun joinGroup(code: String): HttpResponse
}
