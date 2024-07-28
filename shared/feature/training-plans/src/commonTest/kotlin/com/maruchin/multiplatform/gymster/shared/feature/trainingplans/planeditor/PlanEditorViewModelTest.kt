import app.cash.turbine.test
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.di.dataTrainingPlansTestModule
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.model.sampleTrainingPlans
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.repository.FakeTrainingPlansRepository
import com.maruchin.multiplatform.gymster.shared.feature.trainingplans.di.featureTrainingPlansModule
import com.maruchin.multiplatform.gymster.shared.feature.trainingplans.planeditor.PlanEditorUiState
import com.maruchin.multiplatform.gymster.shared.feature.trainingplans.planeditor.PlanEditorViewModel
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
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
class PlanEditorViewModelTest : KoinTest {

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
    fun `emit plan when available`() = runTest {
        trainingPlansRepository.setPlans(sampleTrainingPlans)
        val plan = sampleTrainingPlans.random()
        val viewModel: PlanEditorViewModel = get { parametersOf(plan.id) }

        viewModel.uiState.test {
            awaitItem() shouldBe PlanEditorUiState.Loading

            awaitItem() shouldBe PlanEditorUiState.Loaded(plan)
        }
    }

    @Test
    fun `emit error when plan not available`() = runTest {
        trainingPlansRepository.setPlans(sampleTrainingPlans)
        val planId = "xyz"
        val viewModel: PlanEditorViewModel = get { parametersOf(planId) }

        viewModel.uiState.test {
            awaitItem() shouldBe PlanEditorUiState.Loading

            awaitItem() shouldBe PlanEditorUiState.Error
        }
    }

    @Test
    fun `delete plan`() = runTest {
        trainingPlansRepository.setPlans(sampleTrainingPlans)
        val plan = sampleTrainingPlans.random()
        val viewModel: PlanEditorViewModel = get { parametersOf(plan.id) }

        trainingPlansRepository.observePlan(planId = plan.id).test {
            awaitItem().shouldNotBeNull()

            viewModel.deletePlan()

            awaitItem().shouldBeNull()
        }
    }

    @Test
    fun `delete day`() = runTest {
        trainingPlansRepository.setPlans(sampleTrainingPlans)
        val plan = sampleTrainingPlans.first()
        val day = plan.days.random()
        val viewModel: PlanEditorViewModel = get { parametersOf(plan.id) }

        trainingPlansRepository.observePlan(planId = plan.id).test {
            awaitItem()!!.days shouldContain day

            viewModel.deleteDay(dayId = day.id)

            awaitItem()!!.days shouldNotContain day
        }
    }

    @Test
    fun `delete exercise`() = runTest {
        trainingPlansRepository.setPlans(sampleTrainingPlans)
        val plan = sampleTrainingPlans.first()
        val day = plan.days.first()
        val exercise = day.exercises.random()
        val viewModel: PlanEditorViewModel = get { parametersOf(plan.id) }

        trainingPlansRepository.observePlan(planId = plan.id).test {
            awaitItem()!!.days.first().exercises shouldContain exercise

            viewModel.deleteExercise(dayId = day.id, exerciseId = exercise.id)

            awaitItem()!!.days.first().exercises shouldNotContain exercise
        }
    }

    @Test
    fun `reorder exercises`() = runTest {
        trainingPlansRepository.setPlans(sampleTrainingPlans)
        val plan = sampleTrainingPlans.first()
        val day = plan.days.first()
        val originalExercises = day.exercises
        val reorderedExercises = day.exercises.shuffled()
        val viewModel: PlanEditorViewModel = get { parametersOf(plan.id) }

        trainingPlansRepository.observePlan(planId = plan.id).test {
            awaitItem()!!.days.first().exercises shouldContainInOrder originalExercises

            viewModel.reorderExercises(
                dayId = day.id,
                exercisesIds = reorderedExercises.map { it.id }
            )

            awaitItem()!!.days.first().exercises shouldContainInOrder reorderedExercises
        }
    }
}
