package com.konradjurkowski.moviehub.feature.movies.domain.model

data class Movie(
    val id: Long,
    val groupId: Long? = null,
    val title: String,
    val overview: String,
    val language: String,
    val adult: Boolean,
    val posterUrl: String? = null,
    val backgroundUrl: String? = null,
    val releaseDate: String? = null,
)
