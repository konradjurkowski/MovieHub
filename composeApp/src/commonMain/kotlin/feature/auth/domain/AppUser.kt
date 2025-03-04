package feature.auth.domain

import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.serialization.Serializable

@Serializable
data class AppUser(
    val userId: String,
    val name: String? = null,
    val email: String? = null,
    val description: String? = null,
    val imageUrl: String? = null,
    val backgroundImageUrl: String? = null,
    val favoriteMovieId: Long? = null,
    val favoriteSeriesId: Long? = null,
)

fun FirebaseUser.toAppUser() = AppUser(userId = uid, email = email)
