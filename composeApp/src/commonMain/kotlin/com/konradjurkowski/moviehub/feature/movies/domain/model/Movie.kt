package com.konradjurkowski.moviehub.feature.movies.domain.model

data class Movie(
    val id: Long? = null,
    val groupId: Long? = null,
    val tmdbId: Long,
    val title: String,
    val overview: String,
    val language: String,
    val adult: Boolean,
    val posterUrl: String? = null,
    val backgroundUrl: String? = null,
    val releaseDate: String? = null,
)
