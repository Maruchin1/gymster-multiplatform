package com.maruchin.gymster.core.database2.room

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.maruchin.gymster.core.database2.dao.ExerciseDao
import com.maruchin.gymster.core.database2.dao.PlanDao
import com.maruchin.gymster.core.database2.dao.PlannedExerciseDao
import com.maruchin.gymster.core.database2.dao.PlannedTrainingDao
import com.maruchin.gymster.core.database2.dao.SetResultDao
import com.maruchin.gymster.core.database2.dao.TrainingDao
import com.maruchin.gymster.core.database2.dao.TrainingWeekDao
import com.maruchin.gymster.core.database2.entity.ExerciseEntity
import com.maruchin.gymster.core.database2.entity.PlanEntity
import com.maruchin.gymster.core.database2.entity.PlannedExerciseEntity
import com.maruchin.gymster.core.database2.entity.PlannedTrainingEntity
import com.maruchin.gymster.core.database2.entity.SetResultEntity
import com.maruchin.gymster.core.database2.entity.TrainingEntity
import com.maruchin.gymster.core.database2.entity.TrainingWeekEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(
    entities = [
        PlanEntity::class,
        PlannedTrainingEntity::class,
        PlannedExerciseEntity::class,
        TrainingWeekEntity::class,
        TrainingEntity::class,
        ExerciseEntity::class,
        SetResultEntity::class
    ],
    version = 1
)
@ConstructedBy(GymsterDatabaseConstructor::class)
internal abstract class GymsterDatabase : RoomDatabase() {
    abstract fun planDao(): PlanDao
    abstract fun plannedTrainingDao(): PlannedTrainingDao
    abstract fun plannedExerciseDao(): PlannedExerciseDao
    abstract fun trainingWeekDao(): TrainingWeekDao
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
