package feature.auth.data.remote

import core.model.Response
import core.utils.Resource
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
    suspend fun getAppUser(refresh: Boolean = false): Resource<AppUser>
    suspend fun getAllAppUsers(): Resource<List<AppUser>>
    suspend fun updateAppUser(appUser: AppUser): Resource<Boolean>
    suspend fun uploadImage(image: ByteArray): Resource<String>
}
