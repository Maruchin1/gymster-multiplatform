package com.maruchin.gymster.feature.trainings.trainingeditor

import app.cash.turbine.test
import com.maruchin.gymster.data.trainings.di.dataTrainingsTestModule
import com.maruchin.gymster.data.trainings.model.sampleTrainings
import com.maruchin.gymster.data.trainings.repository.FakeTrainingsRepository
import com.maruchin.gymster.feature.trainings.di.featureTrainingsModule
import io.kotest.matchers.shouldBe
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.parameter.parametersOf
import org.koin.test.KoinTest
import org.koin.test.inject

@OptIn(ExperimentalCoroutinesApi::class)
class TrainingEditorViewModelTest : KoinTest {

    private val selectedTraining = sampleTrainings.first()
    private val trainingsRepository: FakeTrainingsRepository by inject()
    private val viewModel: TrainingEditorViewModel by inject { parametersOf(selectedTraining.id) }

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        startKoin { modules(featureTrainingsModule, dataTrainingsTestModule) }
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
        stopKoin()
    }

    @Test
    fun `emit loaded state when selected training available`() = runTest {
        trainingsRepository.setTrainings(sampleTrainings)

        viewModel.uiState.test {
            awaitItem() shouldBe TrainingEditorUiState.Loading

            awaitItem() shouldBe TrainingEditorUiState.Loaded(selectedTraining)
        }
    }

    @Test
    fun `should not emit when selected training not available`() = runTest {
        trainingsRepository.setTrainings(emptyList())

        viewModel.uiState.test {
            awaitItem() shouldBe TrainingEditorUiState.Loading

            expectNoEvents()
        }
    }
}
