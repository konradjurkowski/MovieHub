package com.konradjurkowski.moviehub.feature.group.data.repository

import com.konradjurkowski.moviehub.core.domain.application.file.FileUploader
import com.konradjurkowski.moviehub.core.domain.model.Response
import com.konradjurkowski.moviehub.core.utils.helpers.safeApiCall
import com.konradjurkowski.moviehub.feature.auth.data.api.dto.response.toDomain
import com.konradjurkowski.moviehub.feature.auth.domain.model.Group
import com.konradjurkowski.moviehub.feature.group.data.api.dto.response.CreateGroupResponse
import com.konradjurkowski.moviehub.feature.group.data.api.dto.response.JoinGroupResponse
import com.konradjurkowski.moviehub.feature.group.domain.api.GroupApi
import com.konradjurkowski.moviehub.feature.group.domain.repository.GroupRepository
import io.ktor.client.call.body

class GroupRepositoryImpl(
    private val groupApi: GroupApi,
    private val fileUploader: FileUploader,
) : GroupRepository {

    override suspend fun createGroup(name: String, description: String, image: ByteArray?): Response<Group> {
        if (image == null) return createGroup(name = name, description = description, imageUrl = null)

        return when (val result = fileUploader.uploadImage(image)) {
            is Response.Success -> createGroup(name = name, description = description, imageUrl = result.data)
            is Response.Failure -> Response.Failure(result.error)
        }
    }

    override suspend fun joinGroup(code: String) =
        safeApiCall(apiCall = { groupApi.joinGroup(code) }) { response ->
            response.body<JoinGroupResponse>().group.toDomain()
        }

    private suspend fun createGroup(name: String, description: String, imageUrl: String? = null) =
        safeApiCall(
            apiCall = { groupApi.createGroup(name = name, description = description, imageUrl = imageUrl) },
            mapper = { response -> response.body<CreateGroupResponse>().group.toDomain() }
        )
}
