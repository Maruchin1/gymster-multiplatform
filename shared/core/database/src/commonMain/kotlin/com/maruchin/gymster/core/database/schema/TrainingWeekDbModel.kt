package com.maruchin.gymster.core.database.schema

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.RealmList

class TrainingWeekDbModel : EmbeddedRealmObject {
    var isComplete: Boolean = false
    var trainings: RealmList<TrainingDbModel> = realmListOf()
}
