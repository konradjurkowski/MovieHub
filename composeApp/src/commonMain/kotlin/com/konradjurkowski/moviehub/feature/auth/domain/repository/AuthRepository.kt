package com.konradjurkowski.moviehub.feature.auth.domain.repository

import com.konradjurkowski.moviehub.core.domain.model.Response
import com.konradjurkowski.moviehub.feature.auth.data.api.dto.response.RegisterResponse
import com.konradjurkowski.moviehub.feature.auth.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val userFlow: Flow<User?>
    suspend fun getUser(): User?
    suspend fun isUserLoggedIn(): Boolean
    suspend fun login(email: String, password: String): Response<User>
    suspend fun register(name: String, email: String, password: String): Response<RegisterResponse>
    suspend fun getUserDetails(): Response<User>
    suspend fun logout(): Response<Unit>
}
