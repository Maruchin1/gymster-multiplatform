package com.maruchin.gymster.core.datastore

import kotlinx.coroutines.flow.Flow

interface Settings {

    fun observeString(key: String): Flow<String?>

    suspend fun setString(key: String, value: String)
}
