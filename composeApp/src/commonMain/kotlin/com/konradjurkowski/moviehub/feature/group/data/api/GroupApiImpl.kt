package com.konradjurkowski.moviehub.feature.group.data.api

import com.konradjurkowski.moviehub.feature.group.data.api.dto.request.CreateGroupRequest
import com.konradjurkowski.moviehub.feature.group.data.api.dto.request.JoinGroupRequest
import com.konradjurkowski.moviehub.feature.group.domain.api.GroupApi
import io.ktor.client.HttpClient
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import io.ktor.http.path

class GroupApiImpl(
    private val httpClient: HttpClient,
) : GroupApi {

    override suspend fun createGroup(
        name: String,
        description: String,
        imageUrl: String?
    ) = httpClient.request {
        val request = CreateGroupRequest(name = name, description = description, imageUrl = imageUrl)
        method = HttpMethod.Post
        url { path("/api/group/create") }
        setBody(request)
    }

    override suspend fun joinGroup(code: String) = httpClient.request {
        val request = JoinGroupRequest(invitationCode = code)
        method = HttpMethod.Post
        url { path("/api/group/join") }
        setBody(request)
    }
}
