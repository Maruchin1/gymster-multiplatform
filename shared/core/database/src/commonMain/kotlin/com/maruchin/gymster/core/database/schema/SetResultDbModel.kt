package com.maruchin.gymster.core.database.schema

import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.RealmUUID

class SetResultDbModel : EmbeddedRealmObject {
    var id: RealmUUID = RealmUUID.random()
    var type: String = ""
    var weight: Double? = null
    var reps: Int? = null
}
