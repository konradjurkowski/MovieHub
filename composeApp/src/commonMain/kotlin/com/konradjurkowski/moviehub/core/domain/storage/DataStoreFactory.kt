package com.konradjurkowski.moviehub.core.domain.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

interface DataStoreFactory {

    fun createDataStore(fileName: String): DataStore<Preferences>
}
