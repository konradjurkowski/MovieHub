package com.konradjurkowski.moviehub.core.data.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(
    val id: Long,
    val name: String,
)
