package com.maruchin.gymster.core.database.room

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.maruchin.gymster.core.database.converter.LocalDateConverter
import com.maruchin.gymster.core.database.dao.ExerciseDao
import com.maruchin.gymster.core.database.dao.PlanDao
import com.maruchin.gymster.core.database.dao.PlannedExerciseDao
import com.maruchin.gymster.core.database.dao.PlannedTrainingDao
import com.maruchin.gymster.core.database.dao.SetResultDao
import com.maruchin.gymster.core.database.dao.TrainingDao
import com.maruchin.gymster.core.database.entity.ExerciseEntity
import com.maruchin.gymster.core.database.entity.PlanEntity
import com.maruchin.gymster.core.database.entity.PlannedExerciseEntity
import com.maruchin.gymster.core.database.entity.PlannedTrainingEntity
import com.maruchin.gymster.core.database.entity.SetResultEntity
import com.maruchin.gymster.core.database.entity.TrainingEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(
    entities = [
        PlanEntity::class,
        PlannedTrainingEntity::class,
        PlannedExerciseEntity::class,
        TrainingEntity::class,
        ExerciseEntity::class,
        SetResultEntity::class
    ],
    version = 1
)
@TypeConverters(LocalDateConverter::class)
@ConstructedBy(GymsterDatabaseConstructor::class)
internal abstract class GymsterDatabase : RoomDatabase() {
    abstract fun planDao(): PlanDao
    abstract fun plannedTrainingDao(): PlannedTrainingDao
    abstract fun plannedExerciseDao(): PlannedExerciseDao
    abstract fun trainingDao(): TrainingDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun setResultDao(): SetResultDao

    companion object {

        fun create(builder: Builder<GymsterDatabase>): GymsterDatabase = builder
            .fallbackToDestructiveMigrationOnDowngrade(dropAllTables = true)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }
}
