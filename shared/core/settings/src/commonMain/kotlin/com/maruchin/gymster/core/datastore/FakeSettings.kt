package com.maruchin.gymster.core.datastore

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeSettings : Settings {

    private val data = MutableStateFlow(emptyMap<String, String>())

    override fun observeString(key: String): Flow<String?> = data.map { it[key] }

    override suspend fun setString(key: String, value: String) {
        val newData = data.value.toMutableMap()
        newData[key] = value
        data.value = newData
    }
}
