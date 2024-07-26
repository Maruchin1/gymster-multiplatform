package com.maruchin.multiplatform.gymster.core.database

import com.maruchin.multiplatform.gymster.core.database.schema.databaseSchema
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

internal fun createTestRealmDatabase(): Realm {
    val config = RealmConfiguration
        .Builder(databaseSchema)
        .inMemory()
        .build()
    return Realm.open(config)
}
