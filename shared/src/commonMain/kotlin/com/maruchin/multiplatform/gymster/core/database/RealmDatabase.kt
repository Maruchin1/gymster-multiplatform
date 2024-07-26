package com.maruchin.multiplatform.gymster.core.database

import com.maruchin.multiplatform.gymster.core.database.schema.databaseSchema
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

internal fun createRealmDatabase(): Realm {
    val config = RealmConfiguration.create(databaseSchema)
    return Realm.open(config)
}
