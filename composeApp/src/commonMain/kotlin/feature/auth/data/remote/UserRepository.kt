package feature.auth.data.remote

import core.utils.Resource
import dev.gitlive.firebase.firestore.DocumentReference
import feature.auth.domain.AppUser

interface UserRepository {
    suspend fun createUser(
        userId: String,
        name: String,
        email: String
    ): Resource<DocumentReference>
    suspend fun getUserById(userId: String): Resource<AppUser>
    suspend fun getAllUsers(): Resource<List<AppUser>>
}
