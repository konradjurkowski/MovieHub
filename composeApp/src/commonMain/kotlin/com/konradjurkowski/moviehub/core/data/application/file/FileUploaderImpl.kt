package com.konradjurkowski.moviehub.core.data.application.file

import com.konradjurkowski.moviehub.core.data.api.file.dto.UploadFileResponse
import com.konradjurkowski.moviehub.core.domain.api.file.CloudinaryApi
import com.konradjurkowski.moviehub.core.domain.application.file.FileUploader
import com.konradjurkowski.moviehub.core.utils.safeApiCall
import io.ktor.client.call.body

class FileUploaderImpl(
    private val api: CloudinaryApi,
) : FileUploader {

    override suspend fun uploadImage(image: ByteArray) =
        safeApiCall(apiCall = { api.uploadImage(image) }) { response ->
            response.body<UploadFileResponse>().secureUrl
        }
}
