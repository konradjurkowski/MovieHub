package com.konradjurkowski.moviehub.feature.movies.data.api.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class AddedTmdbIdsResponse(val movies: List<Long>)
