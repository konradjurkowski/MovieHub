package com.konradjurkowski.moviehub.core.domain.model.media

data class Cast(
    val id: Long,
    val name: String,
    val character: String,
    val popularity: Double,
    val imageUrl: String?,
    val adult: Boolean,
    val gender: Long,
    val order: Long,
)
