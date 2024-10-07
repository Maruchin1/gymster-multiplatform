package com.maruchin.gymster.core.database2.di

import com.maruchin.gymster.core.database2.dao.ExerciseDao
import com.maruchin.gymster.core.database2.dao.FakeExerciseDao
import com.maruchin.gymster.core.database2.dao.FakePlanDao
import com.maruchin.gymster.core.database2.dao.FakePlannedExerciseDao
import com.maruchin.gymster.core.database2.dao.FakePlannedTrainingDao
import com.maruchin.gymster.core.database2.dao.FakeSetResultDao
import com.maruchin.gymster.core.database2.dao.FakeTrainingDao
import com.maruchin.gymster.core.database2.dao.PlanDao
import com.maruchin.gymster.core.database2.dao.PlannedExerciseDao
import com.maruchin.gymster.core.database2.dao.PlannedTrainingDao
import com.maruchin.gymster.core.database2.dao.SetResultDao
import com.maruchin.gymster.core.database2.dao.TrainingDao
import com.maruchin.gymster.core.database2.room.FakeGymsterDatabase
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
