package com.konradjurkowski.moviehub.core.domain.model.media

data class Crew(
    val id: Long,
    val name: String,
    val popularity: Double,
    val imageUrl: String?,
    val adult: Boolean,
    val gender: Long,
    val department: String,
    val job: String,
)
