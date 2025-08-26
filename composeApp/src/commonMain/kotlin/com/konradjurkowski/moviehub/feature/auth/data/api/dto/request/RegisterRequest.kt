package com.konradjurkowski.moviehub.feature.auth.data.api.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
)
