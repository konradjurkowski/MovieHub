package com.konradjurkowski.moviehub.feature.auth.data.api.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val user: UserDto,
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long,
)
