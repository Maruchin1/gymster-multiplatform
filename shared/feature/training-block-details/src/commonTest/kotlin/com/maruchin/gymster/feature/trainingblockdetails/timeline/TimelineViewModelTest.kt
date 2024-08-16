package com.maruchin.gymster.feature.trainingblockdetails.timeline

import app.cash.turbine.test
import com.maruchin.gymster.data.trainings.di.dataTrainingsTestModule
import com.maruchin.gymster.data.trainings.model.sampleTrainingBlocks
import com.maruchin.gymster.data.trainings.repository.FakeTrainingsRepository
import com.maruchin.gymster.feature.trainingblockdetails.di.featureTrainingBlockDetailsModule
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
class TimelineViewModelTest : KoinTest {

    private val trainingBlock = sampleTrainingBlocks.first()
    private val trainingsRepository: FakeTrainingsRepository by inject()
    private val viewModel: TimelineViewModel by inject { parametersOf(trainingBlock.id) }

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        startKoin { modules(featureTrainingBlockDetailsModule, dataTrainingsTestModule) }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `emit loaded state when training block is available`() = runTest {
        trainingsRepository.setTrainingBlocks(sampleTrainingBlocks)

        viewModel.uiState.test {
            awaitItem() shouldBe TimelineUiState.Loading

            awaitItem() shouldBe TimelineUiState.Loaded(trainingBlock)
        }
    }

    @Test
    fun `do not emit new state when training block is not available`() = runTest {
        trainingsRepository.setTrainingBlocks(emptyList())

        viewModel.uiState.test {
            awaitItem() shouldBe TimelineUiState.Loading

            expectNoEvents()
        }
    }
}
