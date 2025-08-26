package com.konradjurkowski.moviehub.core.data.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

class AndroidDataStoreFactory(
    private val context: Context,
) : DataStoreFactory {

    override fun createDataStore(fileName: String): DataStore<Preferences> {
        val filePath = context.filesDir.resolve(fileName).absolutePath.toPath()
        return PreferenceDataStoreFactory.createWithPath(produceFile = { filePath })
    }
}
