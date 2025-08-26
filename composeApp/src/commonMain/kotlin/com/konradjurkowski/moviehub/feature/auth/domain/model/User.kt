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
    val groups: List<Group> = emptyList(),
)

@Serializable
data class Group(
    val id: Long,
    val name: String,
    val description: String = "",
    val imageUrl: String? = null,
    val inviteCode: String,
    val members: List<User> = emptyList(),
    val admins: List<User> = emptyList(),
    val bannedUsers: List<User> = emptyList(),
)

enum class UserRole {
    USER, ADMIN
}

fun User.isInAnyGroup(): Boolean = groups.isNotEmpty()
