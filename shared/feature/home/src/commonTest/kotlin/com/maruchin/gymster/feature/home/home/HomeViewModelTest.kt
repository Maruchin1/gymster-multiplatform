package com.maruchin.gymster.feature.home.home

import app.cash.turbine.test
import com.maruchin.gymster.data.trainings.di.dataTrainingsTestModule
import com.maruchin.gymster.data.trainings.model.sampleTrainingBlocks
import com.maruchin.gymster.data.trainings.repository.FakeTrainingsRepository
import com.maruchin.gymster.feature.home.di.featureHomeModule
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
class HomeViewModelTest : KoinTest {

    private val trainingsRepository: FakeTrainingsRepository by inject()
    private val viewModel: HomeViewModel by inject()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        startKoin { modules(featureHomeModule, dataTrainingsTestModule) }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `emit loaded state when there is at least one training block`() = runTest {
        trainingsRepository.setTrainingBlocks(sampleTrainingBlocks)

        viewModel.uiState.test {
            awaitItem() shouldBe HomeUiState.Loading

            awaitItem() shouldBe HomeUiState.Loaded(sampleTrainingBlocks)
        }
    }
}
