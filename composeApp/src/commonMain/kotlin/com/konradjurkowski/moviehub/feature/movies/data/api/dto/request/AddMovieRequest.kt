package com.konradjurkowski.moviehub.feature.movies.data.api.dto.request

import com.konradjurkowski.moviehub.feature.movies.domain.model.Movie
import com.konradjurkowski.moviehub.feature.movies.domain.model.MovieDetails
import kotlinx.serialization.Serializable

@Serializable
data class AddMovieRequest(
    val tmdbId: Long,
    val title: String,
    val overview: String,
    val language: String,
    val adult: Boolean,
    val posterUrl: String? = null,
    val backgroundUrl: String? = null,
    val releaseDate: String? = null,
)

fun Movie.toCreateRequest(): AddMovieRequest {
    return AddMovieRequest(
        tmdbId = tmdbId,
        title = title,
        overview = overview,
        language = language,
        adult = adult,
        posterUrl = posterUrl,
        backgroundUrl = backgroundUrl,
        releaseDate = releaseDate,
    )
}

fun MovieDetails.toCreateRequest(): AddMovieRequest {
    return AddMovieRequest(
        tmdbId = tmdbId,
        title = title,
        overview = overview,
        language = language,
        adult = adult,
        posterUrl = posterUrl,
        backgroundUrl = backgroundUrl,
        releaseDate = releaseDate,
    )
}
