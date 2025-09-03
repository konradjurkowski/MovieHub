package com.konradjurkowski.moviehub.core.data.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class SpokenLanguageDto(
    val name: String,
    val iso_639_1: String,
    val englishName: String,
)
