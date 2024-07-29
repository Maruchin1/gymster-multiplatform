import app.cash.turbine.test
import com.maruchin.gymster.data.plans.di.dataPlansTestModule
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets
import com.maruchin.gymster.data.plans.model.samplePlans
import com.maruchin.gymster.data.plans.repository.FakePlansRepository
import com.maruchin.gymster.feature.plans.di.featureTrainingPlansModule
import com.maruchin.gymster.feature.plans.exerciseform.ExerciseFormViewModel
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
class ExerciseFormViewModelTest : KoinTest {

    private val trainingPlansRepository: FakePlansRepository by inject()

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
    fun `emit exercise when selected and available`() = runTest {
        trainingPlansRepository.setPlans(samplePlans)
        val plan = samplePlans.first()
        val day = plan.days.first()
        val exercise = day.exercises.random()
        val viewModel: ExerciseFormViewModel = get { parametersOf(plan.id, day.id, exercise.id) }

        viewModel.exercise.test {
            awaitItem().shouldBeNull()

            awaitItem() shouldBe exercise
        }
    }

    @Test
    fun `emit null when exercise not selected`() = runTest {
        trainingPlansRepository.setPlans(samplePlans)
        val plan = samplePlans.first()
        val day = plan.days.first()
        val viewModel: ExerciseFormViewModel = get { parametersOf(plan.id, day.id, null) }

        viewModel.exercise.test {
            awaitItem().shouldBeNull()
        }
    }

    @Test
    fun `add new exercise when exercise not selected`() = runTest {
        trainingPlansRepository.setPlans(samplePlans)
        val plan = samplePlans.first()
        val day = plan.days.last()
        val exerciseName = "Bench press"
        val exerciseSets = Sets(regular = 3)
        val exerciseReps = Reps(10..12)
        val viewModel: ExerciseFormViewModel = get { parametersOf(plan.id, day.id, null) }

        trainingPlansRepository.observePlan(planId = plan.id).test {
            awaitItem()!!.days.last().exercises.shouldBeEmpty()

            viewModel.saveExercise(name = exerciseName, sets = exerciseSets, reps = exerciseReps)

            awaitItem()!!.let { plan ->
                plan.days.last().exercises.let { exercises ->
                    exercises shouldHaveSize 1
                    exercises.first().let { exercise ->
                        exercise.name shouldBe exerciseName
                        exercise.sets shouldBe exerciseSets
                        exercise.reps shouldBe exerciseReps
                    }
                }
            }
        }
    }

    @Test
    fun `change exercise name when exercise selected`() = runTest {
        trainingPlansRepository.setPlans(samplePlans)
        val plan = samplePlans.first()
        val day = plan.days.first()
        val exercise = day.exercises.first()
        val exerciseNewName = "Pull ups"
        val exerciseNewSets = Sets(regular = 2, drop = 1)
        val exerciseNewReps = Reps(10..20)
        val viewModel: ExerciseFormViewModel = get { parametersOf(plan.id, day.id, exercise.id) }

        trainingPlansRepository.observePlan(planId = plan.id).test {
            awaitItem()!!.days.first().exercises.first().let {
                it.name shouldBe exercise.name
                it.sets shouldBe exercise.sets
                it.reps shouldBe exercise.reps
            }

            viewModel.saveExercise(
                name = exerciseNewName,
                sets = exerciseNewSets,
                reps = exerciseNewReps
            )

            awaitItem()!!.days.first().exercises.first().let {
                it.name shouldBe exerciseNewName
                it.sets shouldBe exerciseNewSets
                it.reps shouldBe exerciseNewReps
            }
        }
    }
}
