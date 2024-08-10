package com.maruchin.gymster.core.database.schema

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID

class TrainingDbModel : RealmObject {
    var id: RealmUUID = RealmUUID.random()
    var name: String = ""
    var exercises: RealmList<ExerciseDbModel> = realmListOf()
}
