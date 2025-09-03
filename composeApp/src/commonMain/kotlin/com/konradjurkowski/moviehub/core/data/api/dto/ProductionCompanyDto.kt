package com.konradjurkowski.moviehub.core.data.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductionCompanyDto(
    val id: Long,
    val name: String,
    val logoUrl: String?,
    val originCountry: String,
)
