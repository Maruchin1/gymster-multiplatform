package com.maruchin.gymster.core.database.schema

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject

class TrainingWeekDbModel : RealmObject {
    var number: Int = 0
    var trainings: RealmList<TrainingDbModel> = realmListOf()
}
