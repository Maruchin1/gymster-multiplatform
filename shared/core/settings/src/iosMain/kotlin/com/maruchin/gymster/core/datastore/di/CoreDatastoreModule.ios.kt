package com.maruchin.gymster.core.datastore.di

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import kotlinx.cinterop.ExperimentalForeignApi
import okio.Path.Companion.toPath
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
internal actual val coreDatastoreModule = module {

    single {
        PreferenceDataStoreFactory.createWithPath {
            val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null
            )
            checkNotNull(documentDirectory)
            val stringPath = "${documentDirectory.path}gymster.preferences_pb"
            stringPath.toPath()
        }
    }
}
