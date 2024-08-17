package feature.auth.data.remote

import core.utils.Resource
import dev.gitlive.firebase.auth.FirebaseUser
import feature.auth.domain.AppUser
import kotlinx.coroutines.flow.Flow

typealias SignInResponse = Resource<FirebaseUser?>
typealias SignUpResponse = Resource<FirebaseUser?>
typealias ResetPasswordResponse = Resource<Unit>
typealias GetUserResponse = Resource<AppUser>

interface AuthService {
    val currentUser: FirebaseUser?
    val authStateChanged: Flow<FirebaseUser?>
    suspend fun signIn(email: String, password: String): SignInResponse
    suspend fun signUp(name: String, email: String, password: String): SignUpResponse
    suspend fun resetPassword(email: String): ResetPasswordResponse
    suspend fun getAppUser(): GetUserResponse
}
