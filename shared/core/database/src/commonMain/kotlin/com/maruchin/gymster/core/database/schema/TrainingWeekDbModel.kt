package com.maruchin.gymster.core.database.schema

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject

class TrainingWeekDbModel : RealmObject {
    var trainings: RealmList<TrainingDbModel> = realmListOf()
}
