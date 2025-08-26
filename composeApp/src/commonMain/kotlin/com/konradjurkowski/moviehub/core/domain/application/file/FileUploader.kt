package com.konradjurkowski.moviehub.core.domain.application.file

import com.konradjurkowski.moviehub.core.domain.model.Response

interface FileUploader {
    suspend fun uploadImage(image: ByteArray): Response<String>
}
