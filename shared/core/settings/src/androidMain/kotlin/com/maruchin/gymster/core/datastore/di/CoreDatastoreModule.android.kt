package com.maruchin.gymster.core.datastore.di

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import okio.Path.Companion.toPath
import org.koin.dsl.module

internal actual val coreDatastoreModule = module {

    single {
        PreferenceDataStoreFactory.createWithPath {
            val context = get<Context>()
            val dataStoreFileName = "gymster.preferences_pb"
            context.filesDir.resolve(dataStoreFileName).absolutePath.toPath()
        }
    }
}
