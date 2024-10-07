package com.maruchin.gymster.shared.feature.trainings2.traininglist

import app.cash.turbine.test
import com.maruchin.gymster.data.trainings2.di.dataTrainings2TestModule
import com.maruchin.gymster.data.trainings2.model.sampleTrainings
import com.maruchin.gymster.data.trainings2.repository.FakeTrainingsRepository
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
import org.koin.test.KoinTest
import org.koin.test.inject

@OptIn(ExperimentalCoroutinesApi::class)
class TrainingListViewModelTest : KoinTest {

    private val trainingsRepository: FakeTrainingsRepository by inject()
    private val viewModel by lazy { TrainingListViewModel() }

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        startKoin { modules(dataTrainings2TestModule) }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `emit training list`() = runTest {
        trainingsRepository.setTrainings(sampleTrainings)

        viewModel.uiState.test {
            awaitItem() shouldBe TrainingListUiState()
            awaitItem() shouldBe TrainingListUiState(trainings = sampleTrainings)
        }
    }
}
