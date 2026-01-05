package com.konradjurkowski.moviehub.feature.auth.data.storage

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.konradjurkowski.moviehub.core.domain.storage.DataStoreFactory
import com.konradjurkowski.moviehub.core.utils.extensions.get
import com.konradjurkowski.moviehub.core.utils.extensions.getFlow
import com.konradjurkowski.moviehub.core.utils.extensions.save
import com.konradjurkowski.moviehub.feature.auth.domain.model.User
import com.konradjurkowski.moviehub.feature.auth.domain.storage.AuthDataStore
import kotlinx.coroutines.flow.first

class AuthDataStoreImpl(dataStoreFactory: DataStoreFactory) : AuthDataStore {

    private companion object {
        const val NAME = "auth.preferences_pb"
        val USER = stringPreferencesKey("USER")
        val ACCESS_TOKEN = stringPreferencesKey("ACCESS_TOKEN")
        val REFRESH_TOKEN = stringPreferencesKey("REFRESH_TOKEN")
        val IS_FIRST_LAUNCH = booleanPreferencesKey("IS_FIRST_LAUNCH")
    }

    private val dataStore = dataStoreFactory.createDataStore(NAME)

    override val userFlow = dataStore.getFlow<User?>(USER)

    override suspend fun getUser(): User? = dataStore.get(key = USER)

    override suspend fun saveUser(user: User) {
        dataStore.save(key = USER, model = user)
    }

    override suspend fun getAccessToken() = dataStore.data.first()[ACCESS_TOKEN]

    override suspend fun saveAccessToken(token: String) {
        dataStore.edit { pref -> pref[ACCESS_TOKEN] = token }
    }

    override suspend fun getRefreshToken() = dataStore.data.first()[REFRESH_TOKEN]

    override suspend fun saveRefreshToken(token: String) {
        dataStore.edit { pref -> pref[REFRESH_TOKEN] = token }
    }

    override suspend fun isFirstLaunch() = dataStore.data.first()[IS_FIRST_LAUNCH] ?: true

    override suspend fun setFirstLaunchCompleted() {
        dataStore.edit { pref -> pref[IS_FIRST_LAUNCH] = false }
    }

    override suspend fun clear() {
        dataStore.edit { pref -> pref.clear() }
    }
}
