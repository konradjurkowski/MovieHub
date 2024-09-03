package feature.auth.domain

import kotlinx.serialization.Serializable

@Serializable
data class AppUser(
    val userId: String,
    val name: String,
    val email: String,
    val description: String? = null,
    val imageUrl: String? = null,
    val backgroundImageUrl: String? = null,
    val favoriteMovieId: Long? = null,
    val favoriteSeriesId: Long? = null,
)
