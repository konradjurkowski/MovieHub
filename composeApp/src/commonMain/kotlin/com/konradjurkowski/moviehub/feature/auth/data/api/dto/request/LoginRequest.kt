package com.konradjurkowski.moviehub.feature.auth.data.api.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val password: String,
)
