import app.cash.turbine.test
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.di.dataTrainingPlansTestModule
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.model.sampleTrainingPlans
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.repository.FakeTrainingPlansRepository
import com.maruchin.multiplatform.gymster.shared.feature.trainingplans.dayform.DayFormViewModel
import com.maruchin.multiplatform.gymster.shared.feature.trainingplans.di.featureTrainingPlansModule
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
class DayFormViewModelTest : KoinTest {

    private val trainingPlansRepository: FakeTrainingPlansRepository by inject()

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
    fun `emit day when selected and available`() = runTest {
        trainingPlansRepository.setPlans(sampleTrainingPlans)
        val plan = sampleTrainingPlans.first()
        val day = plan.days.random()
        val viewModel: DayFormViewModel = get { parametersOf(plan.id, day.id) }

        viewModel.day.test {
            awaitItem().shouldBeNull()

            awaitItem() shouldBe day
        }
    }

    @Test
    fun `emit null when day not selected`() = runTest {
        trainingPlansRepository.setPlans(sampleTrainingPlans)
        val plan = sampleTrainingPlans.first()
        val viewModel: DayFormViewModel = get { parametersOf(plan.id, null) }

        viewModel.day.test {
            awaitItem().shouldBeNull()

            expectNoEvents()
        }
    }

    @Test
    fun `add new day on save when day not selected`() = runTest {
        trainingPlansRepository.setPlans(sampleTrainingPlans)
        val plan = sampleTrainingPlans.last()
        val dayName = "Push 1"
        val viewModel: DayFormViewModel = get { parametersOf(plan.id, null) }

        trainingPlansRepository.observePlan(plan.id).test {
            awaitItem()!!.days.shouldBeEmpty()

            viewModel.saveDay(name = dayName)

            awaitItem()!!.let {
                it.days shouldHaveSize 1
                it.days.first().name shouldBe dayName
            }
        }
    }

    @Test
    fun `change day name on save when day selected`() = runTest {
        trainingPlansRepository.setPlans(sampleTrainingPlans)
        val plan = sampleTrainingPlans.first()
        val day = plan.days[2]
        val dayNewName = "Push Hyper"
        val viewModel: DayFormViewModel = get { parametersOf(plan.id, day.id) }

        trainingPlansRepository.observePlan(plan.id).test {
            awaitItem()!!.days[2].name shouldBe day.name

            viewModel.saveDay(name = dayNewName)

            awaitItem()!!.days[2].name shouldBe dayNewName
        }
    }
}
