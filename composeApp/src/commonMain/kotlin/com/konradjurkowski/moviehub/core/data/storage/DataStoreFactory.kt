package com.konradjurkowski.moviehub.core.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

interface DataStoreFactory {

    fun createDataStore(fileName: String): DataStore<Preferences>
}
