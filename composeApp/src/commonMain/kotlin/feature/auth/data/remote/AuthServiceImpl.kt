package feature.auth.data.remote

import core.utils.FailureResponseException
import core.utils.Resource
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

class AuthServiceImpl(
    private val auth: FirebaseAuth,
    private val userRepository: UserRepository
) : AuthService {
    override val currentUser: FirebaseUser? = auth.currentUser

    override val authStateChanged: Flow<FirebaseUser?> = auth.authStateChanged

    override suspend fun signIn(email: String, password: String): SignInResponse {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password)
            Resource.Success(result.user)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun signUp(name: String, email: String, password: String): SignUpResponse {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password)

            val firebaseUser = result.user

            if (firebaseUser != null) {
                userRepository.createUser(
                    userId = firebaseUser.uid,
                    name = name,
                    email = email
                )
                Resource.Success(result.user)
            } else {
                Resource.Failure(FailureResponseException())
            }
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun resetPassword(email: String): ResetPasswordResponse {
        return try {
            val result = auth.sendPasswordResetEmail(email)
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }
}
