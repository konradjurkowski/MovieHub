package com.konradjurkowski.moviehub.feature.auth.domain.storage

import com.konradjurkowski.moviehub.feature.auth.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthDataStore {
    val userFlow: Flow<User?>
    suspend fun getUser(): User?
    suspend fun saveUser(user: User)
    suspend fun getAccessToken(): String?
    suspend fun saveAccessToken(token: String)
    suspend fun getRefreshToken(): String?
    suspend fun saveRefreshToken(token: String)
    suspend fun clear()
}
