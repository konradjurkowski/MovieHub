package feature.auth.data.remote

import core.utils.FailureResponseException
import core.utils.FirebaseConstants
import core.utils.Resource
import core.utils.UserExistException
import dev.gitlive.firebase.firestore.DocumentReference
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.where
import feature.auth.domain.AppUser

class UserRepositoryImpl(
    private val firestore: FirebaseFirestore
) : UserRepository {
    override suspend fun createUser(
        userId: String,
        name: String,
        email: String
    ): Resource<DocumentReference> {
        return try {
            val appUser = AppUser(userId, name, email)

            val querySnapshot = firestore
                .collection(FirebaseConstants.USERS_COLLECTION)
                .get()

            val users = querySnapshot.documents.map { it.data(AppUser.serializer()) }

            val userExists = users.any { it.userId == appUser.userId }

            if (userExists) {
                Resource.Failure(UserExistException())
            } else {
                val result = firestore
                    .collection(FirebaseConstants.USERS_COLLECTION)
                    .add(appUser)
                Resource.Success(result)
            }
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun getUserById(userId: String): Resource<AppUser> {
        return try {
            val querySnapshot = firestore
                .collection(FirebaseConstants.USERS_COLLECTION)
                .where {
                    FirebaseConstants.USER_ID equalTo userId
                }
                .get()
            val appUser = querySnapshot.documents
                .map { it.data(AppUser.serializer()) }
                .firstOrNull()
            appUser?.let {
                Resource.Success(it)
            } ?: Resource.Failure(FailureResponseException())
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun getAllUsers(): Resource<List<AppUser>> {
        return try {
            val querySnapshot = firestore
                .collection(FirebaseConstants.USERS_COLLECTION)
                .get()
            val users = querySnapshot.documents.map { it.data(AppUser.serializer()) }
            Resource.Success(users)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }
}
