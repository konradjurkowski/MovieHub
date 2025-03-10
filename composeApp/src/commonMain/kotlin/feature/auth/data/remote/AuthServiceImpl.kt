package feature.auth.data.remote

import core.model.Response
import core.utils.FailureResponseException
import core.utils.constants.FirebaseConstants
import core.utils.UserExistException
import core.utils.getFirebaseData
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.firestore.DocumentReference
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.storage.FirebaseStorage
import feature.auth.domain.AppUser
import feature.auth.domain.toAppUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.Clock

class AuthServiceImpl(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
) : AuthService {

    private val _appUser = MutableStateFlow(auth.currentUser?.toAppUser())
    override val appUser: StateFlow<AppUser?> = _appUser

    override val currentUser: AppUser? get() = _appUser.value ?: auth.currentUser?.toAppUser()

    override suspend fun signIn(email: String, password: String): Response<FirebaseUser?> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password)
            getAppUser(refresh = true)
            Response.Success(result.user)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun signUp(
        name: String,
        email: String,
        password: String
    ): Response<FirebaseUser?> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password)
            val firebaseUser = result.user

            if (firebaseUser != null) {
                createUser(userId = firebaseUser.uid, name = name, email = email)
                Response.Success(result.user)
            } else {
                Response.Failure(FailureResponseException())
            }
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun logout() {
        try {
            auth.signOut()
            _appUser.value = null
        } catch (e: Exception) {
            // NO - OP
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): Response<Unit> {
        return try {
            val result = auth.sendPasswordResetEmail(email)
            Response.Success(result)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun getAppUser(refresh: Boolean): Response<AppUser> {
        if (_appUser.value != null && !refresh) return Response.Success(_appUser.value!!)

        val result = getUserById(currentUser?.userId ?: "")
        if (result.isSuccess()) _appUser.value = result.getSuccess()
        return result
    }

    override suspend fun getAllAppUsers(): Response<List<AppUser>> {
        return try {
            val querySnapshot = firestore
                .collection(FirebaseConstants.USERS_COLLECTION)
                .get()
            val users = querySnapshot.documents.map { it.data(AppUser.serializer()) }
            val appUser = users.firstOrNull { it.userId == currentUser?.userId }
            if (appUser != null) _appUser.value = appUser
            Response.Success(users)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun updateAppUser(appUser: AppUser): Response<Boolean> {
        return try {
            val querySnapshot = firestore
                .collection(FirebaseConstants.USERS_COLLECTION)
                .where { FirebaseConstants.USER_ID equalTo appUser.userId }
                .get()
            val document = querySnapshot.documents.firstOrNull() ?: return Response.Success(true)
            firestore
                .collection(FirebaseConstants.USERS_COLLECTION)
                .document(document.id)
                .update(appUser)
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun uploadImage(image: ByteArray): Response<String> {
        return try {
            val uuid = Clock.System.now().toEpochMilliseconds().toString()
            val data = getFirebaseData(image)
            storage.reference.child(uuid).putData(data)
            val imageUrl = storage.reference.child(uuid).getDownloadUrl()
            return Response.Success(imageUrl)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    private suspend fun createUser(
        userId: String,
        name: String,
        email: String,
    ): Response<DocumentReference> {
        return try {
            val appUser = AppUser(userId, name, email)

            val querySnapshot = firestore
                .collection(FirebaseConstants.USERS_COLLECTION)
                .get()

            val users = querySnapshot.documents.map { it.data(AppUser.serializer()) }
            val userExists = users.any { it.userId == appUser.userId }
            if (userExists) return Response.Failure(UserExistException())

            val result = firestore
                .collection(FirebaseConstants.USERS_COLLECTION)
                .add(appUser)
            Response.Success(result)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    private suspend fun getUserById(userId: String): Response<AppUser> {
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

            if (appUser == null) return Response.Failure(FailureResponseException())
            Response.Success(appUser)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }
}
