package com.konradjurkowski.moviehub.feature.auth.data.api.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(val user: UserDto)
