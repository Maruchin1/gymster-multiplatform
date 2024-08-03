package com.maruchin.gymster.core.database.schema

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.annotations.PersistedName
import io.realm.kotlin.types.annotations.PrimaryKey

class PlanDbModel : RealmObject {
    @PersistedName("_id")
    @PrimaryKey
    var id: RealmUUID = RealmUUID.random()
    var name: String = ""
    var weeksDuration: Int = 0
    var days: RealmList<PlannedTrainingDbModel> = realmListOf()
}
