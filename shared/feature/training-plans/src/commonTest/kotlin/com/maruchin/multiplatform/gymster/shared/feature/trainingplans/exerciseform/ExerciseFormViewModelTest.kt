import app.cash.turbine.test
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.di.dataTrainingPlansTestModule
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.model.Reps
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.model.Sets
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.repository.FakeTrainingPlansRepository
import com.maruchin.multiplatform.gymster.shared.feature.trainingplans.di.featureTrainingPlansModule
import com.maruchin.multiplatform.gymster.shared.feature.trainingplans.exerciseform.ExerciseFormViewModel
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
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
class ExerciseFormViewModelTest : KoinTest {

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
    fun `emit exercise when selected and available`() = runTest {
        trainingPlansRepository.setAvailablePlans()
        val plan = trainingPlansRepository.getPlans().first()
        val day = plan.days.first()
        val exercise = day.exercises.random()
        val viewModel: ExerciseFormViewModel = get { parametersOf(plan.id, day.id, exercise.id) }

        viewModel.exercise.test {
            assertNull(awaitItem())

            assertEquals(exercise, awaitItem())
        }
    }

    @Test
    fun `emit null when exercise not selected`() = runTest {
        trainingPlansRepository.setAvailablePlans()
        val plan = trainingPlansRepository.getPlans().first()
        val day = plan.days.first()
        val viewModel: ExerciseFormViewModel = get { parametersOf(plan.id, day.id, null) }

        viewModel.exercise.test {
            assertNull(awaitItem())
        }
    }

    @Test
    fun `add new exercise when exercise not selected`() = runTest {
        trainingPlansRepository.setAvailablePlans()
        val plan = trainingPlansRepository.getPlans().first()
        val day = plan.days.last()
        val exerciseName = "Bench press"
        val exerciseSets = Sets(regular = 3)
        val exerciseReps = Reps(10..12)
        val viewModel: ExerciseFormViewModel = get { parametersOf(plan.id, day.id, null) }

        trainingPlansRepository.observePlan(planId = plan.id).test {
            assertEquals(0, awaitItem()!!.days.last().exercises.size)

            viewModel.saveExercise(name = exerciseName, sets = exerciseSets, reps = exerciseReps)

            awaitItem()!!.let { plan ->
                plan.days.last().exercises.let { exercises ->
                    assertEquals(1, exercises.size)
                    exercises.first().let { exercise ->
                        assertEquals(exerciseName, exercise.name)
                        assertEquals(exerciseSets, exercise.sets)
                        assertEquals(exerciseReps, exercise.reps)
                    }
                }
            }
        }
    }

    @Test
    fun `change exercise name when exercise selected`() = runTest {
        trainingPlansRepository.setAvailablePlans()
        val plan = trainingPlansRepository.getPlans().first()
        val day = plan.days.first()
        val exercise = day.exercises.first()
        val exerciseNewName = "Pull ups"
        val exerciseNewSets = Sets(regular = 2, drop = 1)
        val exerciseNewReps = Reps(10..20)
        val viewModel: ExerciseFormViewModel = get { parametersOf(plan.id, day.id, exercise.id) }

        trainingPlansRepository.observePlan(planId = plan.id).test {
            assertEquals(exercise.name, awaitItem()!!.days.first().exercises.first().name)

            viewModel.saveExercise(
                name = exerciseNewName,
                sets = exerciseNewSets,
                reps = exerciseNewReps
            )

            assertEquals(exerciseNewName, awaitItem()!!.days.first().exercises.first().name)
        }
    }
}
