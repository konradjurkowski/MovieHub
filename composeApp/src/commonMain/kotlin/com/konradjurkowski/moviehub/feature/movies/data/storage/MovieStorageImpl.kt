package com.konradjurkowski.moviehub.feature.movies.data.storage

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.konradjurkowski.moviehub.core.data.storage.DataStoreFactory
import com.konradjurkowski.moviehub.feature.movies.domain.storage.MovieStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class MovieStorageImpl(dataStoreFactory: DataStoreFactory) : MovieStorage {

    private companion object {
        const val NAME = "movie.preferences_pb"
        val TMDB_IDS = stringSetPreferencesKey("TMDB_IDS")
    }

    private val dataStore = dataStoreFactory.createDataStore(NAME)

    override val tmdbIdsFlow: Flow<List<Long>> = dataStore.data.map { pref ->
        pref[TMDB_IDS]?.map { it.toLong() } ?: emptyList()
    }

    override suspend fun getTmdbIds(): List<Long> {
        return dataStore.data.first()[TMDB_IDS]?.map { it.toLong() } ?: emptyList()
    }

    override suspend fun saveTmdbIds(ids: List<Long>) {
        dataStore.edit { pref ->
            pref[TMDB_IDS] = ids.map { it.toString() }.toSet()
        }
    }

    override suspend fun saveTmdbId(id: Long) {
        dataStore.edit { pref ->
            val currentIds = pref[TMDB_IDS] ?: emptySet()
            pref[TMDB_IDS] = (currentIds + id.toString()).toSet()
        }
    }

    override suspend fun removeTmdbId(id: Long) {
        dataStore.edit { pref ->
            val currentIds = pref[TMDB_IDS] ?: emptySet()
            pref[TMDB_IDS] = currentIds - id.toString()
        }
    }

    override suspend fun clearTmdbIds() {
        dataStore.edit { pref -> pref.clear() }
    }
}
