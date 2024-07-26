package com.maruchin.multiplatform.gymster.core.database.schema

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmUUID

internal class TrainingPlanDayDbModel : EmbeddedRealmObject {
    var id: RealmUUID = RealmUUID.random()
    var name: String = ""
    var exercises: RealmList<TrainingPlanExerciseDbModel> = realmListOf()
}
