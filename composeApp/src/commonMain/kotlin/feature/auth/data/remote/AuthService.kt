package feature.auth.data.remote

import core.utils.Resource
import dev.gitlive.firebase.auth.FirebaseUser
import feature.auth.domain.AppUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AuthService {
    val currentUser: FirebaseUser?
    val appUser: StateFlow<AppUser?>
    val authStateChanged: Flow<FirebaseUser?>
    suspend fun signIn(email: String, password: String): Resource<FirebaseUser?>
    suspend fun signUp(name: String, email: String, password: String): Resource<FirebaseUser?>
    suspend fun logout()
    suspend fun resetPassword(email: String): Resource<Unit>
    suspend fun getAppUser(refresh: Boolean = false): Resource<AppUser>
    suspend fun getAllAppUsers(): Resource<List<AppUser>>
    suspend fun updateAppUser(appUser: AppUser): Resource<Boolean>
    suspend fun uploadImage(image: ByteArray): Resource<String>
}
