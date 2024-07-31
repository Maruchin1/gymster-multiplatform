import app.cash.turbine.test
import com.maruchin.gymster.data.plans.di.dataPlansTestModule
import com.maruchin.gymster.data.plans.model.samplePlans
import com.maruchin.gymster.data.plans.repository.FakePlansRepository
import com.maruchin.gymster.feature.plans.di.featurePlansModule
import com.maruchin.gymster.feature.plans.planform.PlanFormViewModel
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldBeNull
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
import org.koin.test.get
import org.koin.test.inject

@OptIn(ExperimentalCoroutinesApi::class)
class PlanFormViewModelTest : KoinTest {

    private val trainingPlansRepository: FakePlansRepository by inject()

    @BeforeTest
    fun setUp() {
        startKoin { modules(featurePlansModule, dataPlansTestModule) }
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `emit plan when selected and available`() = runTest {
        trainingPlansRepository.setPlans(samplePlans)
        val plan = samplePlans.random()
        val viewModel: PlanFormViewModel = get { parametersOf(plan.id) }

        viewModel.plan.test {
            awaitItem().shouldBeNull()

            awaitItem() shouldBe plan
        }
    }

    @Test
    fun `emit null when plan selected but not available`() = runTest {
        trainingPlansRepository.setPlans(emptyList())
        val planId = "xyz"
        val viewModel: PlanFormViewModel = get { parametersOf(planId) }

        viewModel.plan.test {
            awaitItem().shouldBeNull()
        }
    }

    @Test
    fun `emit null when plan not selected`() = runTest {
        val viewModel: PlanFormViewModel = get { parametersOf(null) }

        viewModel.plan.test {
            awaitItem().shouldBeNull()
        }
    }

    @Test
    fun `change plan name when save and plan selected`() = runTest {
        trainingPlansRepository.setPlans(samplePlans)
        val plan = samplePlans.random()
        val newName = "Eat and sleep"
        val viewModel: PlanFormViewModel = get { parametersOf(plan.id) }

        trainingPlansRepository.observePlan(plan.id).test {
            awaitItem() shouldBe plan

            viewModel.savePlan(newName).join()

            awaitItem()!!.name shouldBe newName
        }
    }

    @Test
    fun `create new plan when save and plan not selected`() = runTest {
        val planName = "Push Pull"
        val viewModel: PlanFormViewModel = get { parametersOf(null) }

        trainingPlansRepository.observeAllPlans().test {
            awaitItem().shouldBeEmpty()

            viewModel.savePlan(planName).join()

            awaitItem().let {
                it shouldHaveSize 1
                it.first().name shouldBe planName
            }
        }
    }
}
