package com.konradjurkowski.moviehub.feature.group.data.api.dto.response

import com.konradjurkowski.moviehub.feature.auth.data.api.dto.response.GroupDto
import kotlinx.serialization.Serializable

@Serializable
data class JoinGroupResponse(val group: GroupDto)
