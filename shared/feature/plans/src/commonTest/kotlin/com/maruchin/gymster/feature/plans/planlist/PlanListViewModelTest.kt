import app.cash.turbine.test
import com.maruchin.gymster.data.plans.di.dataPlansTestModule
import com.maruchin.gymster.data.plans.model.samplePlans
import com.maruchin.gymster.data.plans.repository.FakePlansRepository
import com.maruchin.gymster.data.trainings.di.dataTrainingsTestModule
import com.maruchin.gymster.data.trainings.repository.FakeTrainingsRepository
import com.maruchin.gymster.feature.plans.di.featurePlansModule
import com.maruchin.gymster.feature.plans.planlist.PlanListUiState
import com.maruchin.gymster.feature.plans.planlist.PlanListViewModel
import io.kotest.matchers.collections.shouldHaveSize
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

    private val plansRepository: FakePlansRepository by inject()
    private val trainingsRepository: FakeTrainingsRepository by inject()
    private val viewModel: PlanListViewModel by inject()

    @BeforeTest
    fun setUp() {
        startKoin { modules(featurePlansModule, dataPlansTestModule, dataTrainingsTestModule) }
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `emit loaded state when training plans available`() = runTest {
        plansRepository.setPlans(samplePlans)

        viewModel.uiState.test {
            awaitItem() shouldBe PlanListUiState.Loading

            awaitItem() shouldBe PlanListUiState.Loaded(samplePlans)
        }
    }

    @Test
    fun `emit empty state when no training plans`() = runTest {
        plansRepository.setPlans(emptyList())

        viewModel.uiState.test {
            awaitItem() shouldBe PlanListUiState.Loading

            awaitItem() shouldBe PlanListUiState.Empty
        }
    }

    @Test
    fun `start new training block`() = runTest {
        val plan = samplePlans.first()
        plansRepository.setPlans(samplePlans)

        trainingsRepository.observeAllTrainingBlocks().test {
            awaitItem().isEmpty()

            viewModel.startNewTrainingBlock(plan.id)

            awaitItem() shouldHaveSize 1
        }
    }
}
