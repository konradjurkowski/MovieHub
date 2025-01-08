package feature.auth.data.remote

import com.mmk.kmpnotifier.notification.NotifierManager
import core.utils.Constants
import core.utils.FailureResponseException
import core.utils.FirebaseConstants
import core.utils.Resource
import core.utils.UserExistException
import core.utils.getFirebaseData
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.firestore.DocumentReference
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.storage.FirebaseStorage
import feature.auth.domain.AppUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.Clock

class AuthServiceImpl(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
) : AuthService {
    // TODO REPLACE ALL INVOKED FirebaseAuth.currentUser in APP
    override val currentUser: FirebaseUser? get() = auth.currentUser
    override val authStateChanged: Flow<FirebaseUser?> = auth.authStateChanged

    private val _appUser = MutableStateFlow<AppUser?>(null)
    override val appUser: StateFlow<AppUser?> = _appUser

    override suspend fun signIn(email: String, password: String): Resource<FirebaseUser?> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password)
            getAppUser(refresh = true)
            Resource.Success(result.user)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun signUp(
        name: String,
        email: String,
        password: String
    ): Resource<FirebaseUser?> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password)
            val firebaseUser = result.user

            if (firebaseUser != null) {
                createUser(userId = firebaseUser.uid, name = name, email = email)
                Resource.Success(result.user)
            } else {
                Resource.Failure(FailureResponseException())
            }
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun logout() {
        try {
            auth.signOut()
            NotifierManager.getPushNotifier().apply {
                deleteMyToken()
                unSubscribeFromTopic(Constants.PUSH_NEWS_TOPIC)
            }
            _appUser.value = null
        } catch (e: Exception) {
            // NO - OP
        }
    }

    override suspend fun resetPassword(email: String): Resource<Unit> {
        return try {
            val result = auth.sendPasswordResetEmail(email)
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun getAppUser(refresh: Boolean): Resource<AppUser> {
        if (_appUser.value != null && !refresh) Resource.Success(_appUser.value)

        val result = getUserById(currentUser?.uid ?: "")
        if (result.isSuccess()) _appUser.value = result.getSuccess()
        return result
    }

    override suspend fun getAllAppUsers(): Resource<List<AppUser>> {
        return try {
            val querySnapshot = firestore
                .collection(FirebaseConstants.USERS_COLLECTION)
                .get()
            val users = querySnapshot.documents.map { it.data(AppUser.serializer()) }
            val appUser = users.firstOrNull { it.userId == currentUser?.uid }
            if (appUser != null) _appUser.value = appUser
            Resource.Success(users)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun updateAppUser(appUser: AppUser): Resource<Boolean> {
        return try {
            val querySnapshot = firestore
                .collection(FirebaseConstants.USERS_COLLECTION)
                .where { FirebaseConstants.USER_ID equalTo appUser.userId }
                .get()
            val document = querySnapshot.documents.firstOrNull() ?: return Resource.Success(true)
            firestore
                .collection(FirebaseConstants.USERS_COLLECTION)
                .document(document.id)
                .update(appUser)
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun uploadImage(image: ByteArray): Resource<String> {
        return try {
            val uuid = Clock.System.now().toEpochMilliseconds().toString()
            val data = getFirebaseData(image)
            storage.reference.child(uuid).putData(data)
            val imageUrl = storage.reference.child(uuid).getDownloadUrl()
            return Resource.Success(imageUrl)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    private suspend fun createUser(
        userId: String,
        name: String,
        email: String,
    ): Resource<DocumentReference> {
        return try {
            val appUser = AppUser(userId, name, email)

            val querySnapshot = firestore
                .collection(FirebaseConstants.USERS_COLLECTION)
                .get()

            val users = querySnapshot.documents.map { it.data(AppUser.serializer()) }
            val userExists = users.any { it.userId == appUser.userId }
            if (userExists) return Resource.Failure(UserExistException())

            val result = firestore
                .collection(FirebaseConstants.USERS_COLLECTION)
                .add(appUser)
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    private suspend fun getUserById(userId: String): Resource<AppUser> {
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

            if (appUser == null) return Resource.Failure(FailureResponseException())
            Resource.Success(appUser)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }
}
