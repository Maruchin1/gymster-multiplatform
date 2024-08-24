package com.maruchin.gymster.core.datastore.di

import com.maruchin.gymster.core.datastore.DefaultSettings
import com.maruchin.gymster.core.datastore.Settings
import org.koin.dsl.bind
import org.koin.dsl.module

val coreSettingsModule = module {

    includes(coreDatastoreModule)

    factory { DefaultSettings(get()) } bind Settings::class
}
