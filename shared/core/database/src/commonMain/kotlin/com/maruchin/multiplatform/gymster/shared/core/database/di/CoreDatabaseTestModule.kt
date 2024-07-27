package com.maruchin.multiplatform.gymster.shared.core.database.di

import com.maruchin.multiplatform.gymster.shared.core.database.schema.databaseSchema
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module

val coreDatabaseTestModule = module {

    single {
        val config = RealmConfiguration
            .Builder(databaseSchema)
            .inMemory()
            .build()
        Realm.open(config)
    }
}
