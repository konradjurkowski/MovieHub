package com.konradjurkowski.moviehub.feature.auth.data.api.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class ActivateAccountRequest(
    val email: String,
    val code: String,
)
