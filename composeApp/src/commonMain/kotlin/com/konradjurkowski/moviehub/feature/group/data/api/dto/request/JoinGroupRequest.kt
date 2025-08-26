package com.konradjurkowski.moviehub.feature.group.data.api.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class JoinGroupRequest(val invitationCode: String)
