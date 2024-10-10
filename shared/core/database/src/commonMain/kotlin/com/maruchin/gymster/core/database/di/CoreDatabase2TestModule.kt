package com.maruchin.gymster.core.database.di

import com.maruchin.gymster.core.database.dao.ExerciseDao
import com.maruchin.gymster.core.database.dao.FakeExerciseDao
import com.maruchin.gymster.core.database.dao.FakePlanDao
import com.maruchin.gymster.core.database.dao.FakePlannedExerciseDao
import com.maruchin.gymster.core.database.dao.FakePlannedTrainingDao
import com.maruchin.gymster.core.database.dao.FakeSetResultDao
import com.maruchin.gymster.core.database.dao.FakeTrainingDao
import com.maruchin.gymster.core.database.dao.PlanDao
import com.maruchin.gymster.core.database.dao.PlannedExerciseDao
import com.maruchin.gymster.core.database.dao.PlannedTrainingDao
import com.maruchin.gymster.core.database.dao.SetResultDao
import com.maruchin.gymster.core.database.dao.TrainingDao
import com.maruchin.gymster.core.database.room.FakeGymsterDatabase
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDatabase2TestModule = module {

    single { FakeGymsterDatabase() }

    factory { FakePlanDao(get()) } bind PlanDao::class

    factory { FakePlannedTrainingDao(get()) } bind PlannedTrainingDao::class

    factory { FakePlannedExerciseDao(get()) } bind PlannedExerciseDao::class

    factory { FakeTrainingDao(get()) } bind TrainingDao::class

    factory { FakeExerciseDao(get()) } bind ExerciseDao::class

    factory { FakeSetResultDao(get()) } bind SetResultDao::class
}
