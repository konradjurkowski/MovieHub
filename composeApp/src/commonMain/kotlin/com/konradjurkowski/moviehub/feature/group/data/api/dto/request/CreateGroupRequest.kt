package com.konradjurkowski.moviehub.feature.group.data.api.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateGroupRequest(
    val name: String,
    val description: String = "",
    val imageUrl: String? = null,
)
