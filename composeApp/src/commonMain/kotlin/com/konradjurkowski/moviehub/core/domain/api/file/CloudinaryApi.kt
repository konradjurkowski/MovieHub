package com.konradjurkowski.moviehub.core.domain.api.file

import io.ktor.client.statement.HttpResponse

interface CloudinaryApi {
    suspend fun uploadImage(image: ByteArray): HttpResponse
}
