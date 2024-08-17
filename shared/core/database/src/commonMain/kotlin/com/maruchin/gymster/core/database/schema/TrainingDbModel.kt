package com.maruchin.gymster.core.database.schema

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmUUID

class TrainingDbModel : EmbeddedRealmObject {
    var id: RealmUUID = RealmUUID.random()
    var week: Int = 0
    var name: String = ""
    var exercises: RealmList<ExerciseDbModel> = realmListOf()
}
