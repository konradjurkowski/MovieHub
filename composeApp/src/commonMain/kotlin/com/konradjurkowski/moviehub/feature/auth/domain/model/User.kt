package com.konradjurkowski.moviehub.feature.auth.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long,
    val email: String,
    val name: String,
    val description: String = "",
    val imageUrl: String? = null,
    val role: UserRole,
)

enum class UserRole {
    USER, ADMIN
}
