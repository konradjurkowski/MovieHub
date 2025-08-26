package com.konradjurkowski.moviehub.core.domain.api

import io.ktor.client.HttpClient

interface ApiClientFactory {
    fun createBaseClient(baseUrl: String): HttpClient
}
