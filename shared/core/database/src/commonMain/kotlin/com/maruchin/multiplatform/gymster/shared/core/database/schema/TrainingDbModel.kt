package com.maruchin.multiplatform.gymster.shared.core.database.schema

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.annotations.PersistedName
import io.realm.kotlin.types.annotations.PrimaryKey

class TrainingDbModel : RealmObject {
    @PersistedName("_id")
    @PrimaryKey
    var id: RealmUUID = RealmUUID.random()
    var date: String = ""
    var exercises: RealmList<TrainingExerciseDbModel> = realmListOf()
}
