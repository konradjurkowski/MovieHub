package com.konradjurkowski.moviehub.core.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

suspend inline fun <reified T> DataStore<Preferences>.save(
    key: Preferences.Key<String>,
    model: T
) = edit { preferences ->
    preferences[key] = Json.encodeToString(model)
}

inline fun <reified T> DataStore<Preferences>.getFlow(
    key: Preferences.Key<String>
): Flow<T?> = data.map { preferences ->
    preferences[key]?.let { json ->
        runCatching { Json.decodeFromString<T>(json) }.getOrNull()
    }
}

suspend inline fun <reified T> DataStore<Preferences>.get(
    key: Preferences.Key<String>
): T? = data.first()[key]?.let { json ->
    runCatching { Json.decodeFromString<T>(json) }.getOrNull()
}
