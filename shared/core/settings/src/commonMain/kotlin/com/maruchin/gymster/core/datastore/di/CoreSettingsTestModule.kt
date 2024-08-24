package com.maruchin.gymster.core.datastore.di

import com.maruchin.gymster.core.datastore.FakeSettings
import com.maruchin.gymster.core.datastore.Settings
import org.koin.dsl.bind
import org.koin.dsl.module

val coreSettingsTestModule = module {

    single { FakeSettings() } bind Settings::class
}
