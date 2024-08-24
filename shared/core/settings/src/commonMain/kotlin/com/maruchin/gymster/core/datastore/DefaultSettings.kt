package com.maruchin.gymster.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class DefaultSettings(private val dataStore: DataStore<Preferences>) : Settings {

    override fun observeString(key: String): Flow<String?> {
        val preferencesKey = stringPreferencesKey(key)
        return dataStore.data.map { it[preferencesKey] }
    }

    override suspend fun setString(key: String, value: String) {
        val preferencesKey = stringPreferencesKey(key)
        dataStore.edit { it[preferencesKey] = value }
    }
}
