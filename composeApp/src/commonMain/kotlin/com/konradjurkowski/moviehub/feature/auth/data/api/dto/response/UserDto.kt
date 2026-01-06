package com.konradjurkowski.moviehub.feature.auth.data.api.dto.response

import com.konradjurkowski.moviehub.feature.auth.domain.model.User
import com.konradjurkowski.moviehub.feature.auth.domain.model.UserRole
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Long,
    val email: String,
    val name: String,
    val description: String = "",
    val imageUrl: String? = null,
    val role: String,
)

fun UserDto.toDomain(): User {
    return User(
        id = this.id,
        email = this.email,
        name = this.name,
        description = this.description,
        imageUrl = this.imageUrl,
        role = UserRole.entries.firstOrNull { it.name == this.role } ?: UserRole.USER,
    )
}
