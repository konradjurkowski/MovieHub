package com.konradjurkowski.moviehub.feature.movies.domain.storage

import kotlinx.coroutines.flow.Flow

interface MovieStorage {
    val tmdbIdsFlow: Flow<List<Long>>
    suspend fun getTmdbIds(): List<Long>
    suspend fun saveTmdbIds(ids: List<Long>)
    suspend fun saveTmdbId(id: Long)
    suspend fun removeTmdbId(id: Long)
    suspend fun clearTmdbIds()
}
