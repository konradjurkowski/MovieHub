package com.konradjurkowski.moviehub.core.data.api.file.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UploadFileResponse(
    @SerialName("secure_url")
    val secureUrl: String,
)
