import app.cash.turbine.test
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.di.dataTrainingPlansTestModule
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.model.sampleTrainingPlans
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.repository.FakeTrainingPlansRepository
import com.maruchin.multiplatform.gymster.shared.feature.trainingplans.di.featureTrainingPlansModule
import com.maruchin.multiplatform.gymster.shared.feature.trainingplans.planlist.PlanListUiState
import com.maruchin.multiplatform.gymster.shared.feature.trainingplans.planlist.PlanListViewModel
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
class PlanListViewModelTest : KoinTest {

    private val trainingPlansRepository: FakeTrainingPlansRepository by inject()
    private val viewModel: PlanListViewModel by inject()

    @BeforeTest
    fun setUp() {
        startKoin { modules(featureTrainingPlansModule, dataTrainingPlansTestModule) }
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `emit loaded state when training plans available`() = runTest {
        trainingPlansRepository.setPlans(sampleTrainingPlans)

        viewModel.uiState.test {
            awaitItem() shouldBe PlanListUiState.Loading

            awaitItem() shouldBe PlanListUiState.Loaded(sampleTrainingPlans)
        }
    }

    @Test
    fun `emit empty state when no training plans`() = runTest {
        trainingPlansRepository.setPlans(emptyList())

        viewModel.uiState.test {
            awaitItem() shouldBe PlanListUiState.Loading

            awaitItem() shouldBe PlanListUiState.Empty
        }
    }
}
