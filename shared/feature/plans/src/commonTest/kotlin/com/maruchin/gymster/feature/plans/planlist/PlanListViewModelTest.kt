import app.cash.turbine.test
import com.maruchin.gymster.data.plans.di.dataPlansTestModule
import com.maruchin.gymster.data.plans.model.samplePlans
import com.maruchin.gymster.data.plans.repository.FakePlansRepository
import com.maruchin.gymster.feature.plans.di.featureTrainingPlansModule
import com.maruchin.gymster.feature.plans.planlist.PlanListUiState
import com.maruchin.gymster.feature.plans.planlist.PlanListViewModel
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

    private val trainingPlansRepository: FakePlansRepository by inject()
    private val viewModel: PlanListViewModel by inject()

    @BeforeTest
    fun setUp() {
        startKoin { modules(featureTrainingPlansModule, dataPlansTestModule) }
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `emit loaded state when training plans available`() = runTest {
        trainingPlansRepository.setPlans(samplePlans)

        viewModel.uiState.test {
            awaitItem() shouldBe PlanListUiState.Loading

            awaitItem() shouldBe PlanListUiState.Loaded(samplePlans)
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
