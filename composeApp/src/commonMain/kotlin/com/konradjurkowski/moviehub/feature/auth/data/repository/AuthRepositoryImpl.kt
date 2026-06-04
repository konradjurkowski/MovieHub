package com.konradjurkowski.moviehub.feature.auth.data.repository

import com.konradjurkowski.moviehub.core.domain.model.Response
import com.konradjurkowski.moviehub.core.utils.helpers.safeApiCall
import com.konradjurkowski.moviehub.core.utils.coroutines.DispatchersProvider
import com.konradjurkowski.moviehub.feature.auth.data.api.dto.response.AuthResponse
import com.konradjurkowski.moviehub.feature.auth.data.api.dto.response.UserDto
import com.konradjurkowski.moviehub.feature.auth.data.api.dto.response.toDomain
import com.konradjurkowski.moviehub.feature.auth.domain.api.AuthApi
import com.konradjurkowski.moviehub.feature.auth.domain.api.UserApi
import com.konradjurkowski.moviehub.feature.auth.domain.model.User
import com.konradjurkowski.moviehub.feature.auth.domain.repository.AuthRepository
import com.konradjurkowski.moviehub.feature.auth.domain.storage.AuthDataStore
import io.ktor.client.call.body
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val userApi: UserApi,
    private val authDataStore: AuthDataStore,
    private val dispatchersProvider: DispatchersProvider,
) : AuthRepository {

    private val scope = CoroutineScope(SupervisorJob() + dispatchersProvider.main)

    override val userFlow = authDataStore.userFlow

    override suspend fun getUser() = authDataStore.getUser()

    override suspend fun isUserLoggedIn() =
        authDataStore.getAccessToken() != null && authDataStore.getRefreshToken() != null && authDataStore.getUser() != null

    override suspend fun isInitialLaunch() = authDataStore.isFirstLaunch()

    override suspend fun setFirstLaunchCompleted() = authDataStore.setFirstLaunchCompleted()

    override suspend fun login(email: String, password: String) =
        safeApiCall(apiCall = { authApi.login(email, password) }) { response ->
            handleLoginResponse(response.body<AuthResponse>())
        }

    override suspend fun register(name: String, email: String, password: String) =
        safeApiCall(apiCall = { authApi.register(name = name, email = email, password = password) }) {}

    override suspend fun activateAccount(email: String, code: String) =
        safeApiCall(apiCall = { authApi.activateAccount(email = email, code = code) }) { response ->
            handleLoginResponse(response.body<AuthResponse>())
        }

    override suspend fun sendActivationCode(email: String) =
        safeApiCall(apiCall = { authApi.sendActivationCode(email) }) {}

    override suspend fun getUserDetails(): Response<User> =
        safeApiCall(apiCall = { userApi.getUserDetails() }) { response ->
            response.body<UserDto>()
                .toDomain()
                .also { authDataStore.saveUser(user = it) }
        }

    override suspend fun clearUserSession() {
        authDataStore.clear()
    }

    override fun logout() {
        scope.launch(dispatchersProvider.io) {
            val refreshToken = authDataStore.getRefreshToken() ?: return@launch
            safeApiCall(apiCall = { authApi.logout(refreshToken = refreshToken) }) { /* NO - OP */ }
        }
    }

    private suspend fun handleLoginResponse(response: AuthResponse) {
        authDataStore.saveUser(response.user.toDomain())
        authDataStore.saveAccessToken(response.accessToken)
        authDataStore.saveRefreshToken(response.refreshToken)
    }
}
