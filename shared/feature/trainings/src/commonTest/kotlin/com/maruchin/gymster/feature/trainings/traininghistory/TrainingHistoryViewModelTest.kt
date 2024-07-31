import app.cash.turbine.test
import com.maruchin.gymster.data.trainings.di.dataTrainingsTestModule
import com.maruchin.gymster.data.trainings.model.sampleTrainings
import com.maruchin.gymster.data.trainings.repository.FakeTrainingsRepository
import com.maruchin.gymster.feature.trainings.di.featureTrainingsModule
import com.maruchin.gymster.feature.trainings.traininghistory.TrainingHistoryUiState
import com.maruchin.gymster.feature.trainings.traininghistory.TrainingHistoryViewModel
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
class TrainingHistoryViewModelTest : KoinTest {

    private val trainingsRepository: FakeTrainingsRepository by inject()
    private val viewModel: TrainingHistoryViewModel by inject()

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
    fun `emit loaded state when trainings are not empty`() = runTest {
        trainingsRepository.setTrainings(sampleTrainings)

        viewModel.uiState.test {
            awaitItem() shouldBe TrainingHistoryUiState.Loading

            awaitItem() shouldBe TrainingHistoryUiState.Loaded(sampleTrainings)
        }
    }

    @Test
    fun `emit empty state when trainings are empty`() = runTest {
        trainingsRepository.setTrainings(emptyList())

        viewModel.uiState.test {
            awaitItem() shouldBe TrainingHistoryUiState.Loading

            awaitItem() shouldBe TrainingHistoryUiState.Empty
        }
    }
}
