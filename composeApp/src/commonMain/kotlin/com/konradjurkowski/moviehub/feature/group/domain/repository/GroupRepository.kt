package com.konradjurkowski.moviehub.feature.group.domain.repository

import com.konradjurkowski.moviehub.core.domain.model.Response
import com.konradjurkowski.moviehub.feature.auth.domain.model.Group

interface GroupRepository {
    suspend fun createGroup(name: String, description: String = "", image: ByteArray? = null): Response<Group>
    suspend fun joinGroup(code: String): Response<Group>
}
