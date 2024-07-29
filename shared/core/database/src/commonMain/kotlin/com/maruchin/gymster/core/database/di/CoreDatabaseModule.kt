package com.maruchin.gymster.core.database.di

import com.maruchin.gymster.core.database.schema.databaseSchema
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module

val coreDatabaseModule = module {

    single {
        val config = RealmConfiguration.create(databaseSchema)
        Realm.open(config)
    }
}
