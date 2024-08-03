package com.maruchin.gymster.core.database.schema

import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.RealmUUID

class PlannedExerciseDbModel : EmbeddedRealmObject {
    var id: RealmUUID = RealmUUID.random()
    var name: String = ""
    var regularSets: Int = 1
    var dropSets: Int = 0
    var minReps: Int = 1
    var maxReps: Int = 2
}
