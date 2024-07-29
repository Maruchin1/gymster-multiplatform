package com.maruchin.multiplatform.gymster.shared.core.database.schema

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID

class TrainingExerciseDbModel : RealmObject {
    var id: RealmUUID = RealmUUID.random()
    var name: String = ""
    var regularSets: Int = 0
    var dropSets: Int = 0
    var minReps: Int = 0
    var maxReps: Int = 0
    var progress: RealmList<ProgressDbModel> = realmListOf()
}
