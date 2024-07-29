package com.maruchin.gymster.core.database.schema

import io.realm.kotlin.types.RealmObject

class ProgressDbModel : RealmObject {
    var weight: Double? = null
    var reps: Int? = null
}
