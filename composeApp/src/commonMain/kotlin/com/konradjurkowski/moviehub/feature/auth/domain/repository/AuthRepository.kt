package com.konradjurkowski.moviehub.feature.auth.domain.repository

import com.konradjurkowski.moviehub.core.domain.model.Response
import com.konradjurkowski.moviehub.feature.auth.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val userFlow: Flow<User?>
    suspend fun getUser(): User?
    suspend fun isUserLoggedIn(): Boolean
    suspend fun isInitialLaunch(): Boolean
    suspend fun setFirstLaunchCompleted()
    suspend fun login(email: String, password: String): Response<Unit>
    suspend fun register(name: String, email: String, password: String): Response<Unit>
    suspend fun activateAccount(email: String, code: String): Response<Unit>
    suspend fun sendActivationCode(email: String): Response<Unit>
    suspend fun getUserDetails(): Response<User>
    suspend fun clearUserSession()
    fun logout()
}
