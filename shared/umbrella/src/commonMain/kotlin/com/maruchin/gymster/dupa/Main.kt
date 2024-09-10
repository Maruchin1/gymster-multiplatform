package com.maruchin.gymster.dupa

import com.maruchin.gymster.core.clock.di.coreClockModule
import com.maruchin.gymster.core.coroutines.coreCoroutinesModule
import com.maruchin.gymster.core.database.di.coreDatabaseModule
import com.maruchin.gymster.core.datastore.di.coreSettingsModule
import com.maruchin.gymster.data.plans.di.dataPlansModule
import com.maruchin.gymster.data.trainings.di.dataTrainingsModule
import com.maruchin.gymster.feature.home.di.featureHomeModule
import com.maruchin.gymster.feature.plans.di.featurePlansModule
import com.maruchin.gymster.feature.trainingblockdetails.di.featureTrainingBlockDetailsModule
import com.maruchin.gymster.feature.trainingeditor.di.featureTrainingEditorModule
import org.koin.core.context.startKoin

private val coreModules =
    listOf(coreDatabaseModule, coreClockModule, coreCoroutinesModule, coreSettingsModule)

private val dataModules = listOf(dataPlansModule, dataTrainingsModule)

private val featureModules = listOf(
    featureHomeModule,
    featurePlansModule,
    featureTrainingBlockDetailsModule,
    featureTrainingEditorModule
)

fun initSharedLibrary(platform: Platform) {
    startKoin {
        modules(coreModules + dataModules + featureModules + platform.module)
    }
}
