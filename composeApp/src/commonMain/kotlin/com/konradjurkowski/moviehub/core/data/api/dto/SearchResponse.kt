package com.konradjurkowski.moviehub.core.data.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse<T>(
    val page: Long,
    val results: List<T>,
    val totalPages: Long,
    val totalResults: Long,
)
