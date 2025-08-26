package com.konradjurkowski.moviehub.feature.auth.data.api.dto.response

import com.konradjurkowski.moviehub.feature.auth.domain.model.Group
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
    val groups: List<GroupDto> = emptyList(),
)

@Serializable
data class GroupDto(
    val id: Long,
    val name: String,
    val description: String,
    val imageUrl: String? = null,
    val invitationCode: String,
    val members: List<UserDto> = emptyList(),
    val admins: List<UserDto> = emptyList(),
    val bannedUsers: List<UserDto> = emptyList(),
)

fun UserDto.toDomain(): User {
    return User(
        id = this.id,
        email = this.email,
        name = this.name,
        description = this.description,
        imageUrl = this.imageUrl,
        role = UserRole.entries.firstOrNull { it.name == this.role } ?: UserRole.USER,
        groups = this.groups.map { it.toDomain() },
    )
}

fun GroupDto.toDomain(): Group {
    return Group(
        id = this.id,
        name = this.name,
        description = this.description,
        imageUrl = this.imageUrl,
        inviteCode = this.invitationCode,
        members = this.members.map { it.toDomain() },
        admins = this.admins.map { it.toDomain() },
        bannedUsers = this.bannedUsers.map { it.toDomain() }
    )
}
