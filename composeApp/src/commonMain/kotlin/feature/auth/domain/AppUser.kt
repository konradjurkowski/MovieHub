package feature.auth.domain

import kotlinx.serialization.Serializable

@Serializable
data class AppUser(
    val userId: String,
    val name: String,
    val email: String,
    val imageUrl: String? = null,
)
