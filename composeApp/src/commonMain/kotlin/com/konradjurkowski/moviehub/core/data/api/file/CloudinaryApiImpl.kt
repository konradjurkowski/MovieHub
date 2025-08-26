package com.konradjurkowski.moviehub.core.data.api.file

import com.konradjurkowski.moviehub.core.domain.api.file.CloudinaryApi
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.submitForm
import io.ktor.http.Parameters
import io.ktor.http.path
import io.ktor.util.encodeBase64

private const val CLOUD_NAME = "drfcdhkqr"
private const val UPLOAD_PRESET = "mobile_uploads"

class CloudinaryApiImpl(
    private val httpClient: HttpClient,
) : CloudinaryApi {

    override suspend fun uploadImage(image: ByteArray) = httpClient.submitForm(
        formParameters = Parameters.build {
            append("upload_preset", UPLOAD_PRESET)
            append("file", "data:image/jpeg;base64,${image.encodeBase64()}")
        }
    ) { url { path("v1_1/$CLOUD_NAME/image/upload") } }
}
