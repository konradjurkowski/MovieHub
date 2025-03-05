package feature.auth.data.remote

import core.model.Response
import dev.gitlive.firebase.auth.FirebaseUser
import feature.auth.domain.AppUser
import kotlinx.coroutines.flow.StateFlow

interface AuthService {
    val currentUser: AppUser?
    val appUser: StateFlow<AppUser?>
    suspend fun signIn(email: String, password: String): Response<FirebaseUser?>
    suspend fun signUp(name: String, email: String, password: String): Response<FirebaseUser?>
    suspend fun logout()
    suspend fun sendPasswordResetEmail(email: String): Response<Unit>
    suspend fun getAppUser(refresh: Boolean = false): Response<AppUser>
    suspend fun getAllAppUsers(): Response<List<AppUser>>
    suspend fun updateAppUser(appUser: AppUser): Response<Boolean>
    suspend fun uploadImage(image: ByteArray): Response<String>
}
