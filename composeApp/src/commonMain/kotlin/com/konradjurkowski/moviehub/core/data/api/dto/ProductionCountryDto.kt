package com.konradjurkowski.moviehub.core.data.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductionCountryDto(
    val name: String,
    val iso_3166_1: String,
)
