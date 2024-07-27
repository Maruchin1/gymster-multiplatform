import app.cash.turbine.test
import com.maruchin.multiplatform.gymster.core.database.di.coreDatabaseTestModule
import com.maruchin.multiplatform.gymster.data.trainingplans.di.dataTrainingPlansModule
import com.maruchin.multiplatform.gymster.data.trainingplans.model.Reps
import com.maruchin.multiplatform.gymster.data.trainingplans.model.Sets
import com.maruchin.multiplatform.gymster.data.trainingplans.repository.TrainingPlansRepository
import io.realm.kotlin.Realm
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class DefaultTrainingPlansRepositoryTest : KoinTest {
    private val realm: Realm by inject()
    private val repository: TrainingPlansRepository by inject()

    @BeforeTest
    fun setup() {
        startKoin { modules(dataTrainingPlansModule, coreDatabaseTestModule) }
    }

    @AfterTest
    fun tearDown() {
        realm.close()
        stopKoin()
    }

    @Test
    fun `create plan`() = runTest {
        val name = "Push Pull"
        repository.observeAllPlans().test {
            assertEquals(emptyList(), awaitItem())

            repository.createPlan(name = name)

            val updatedList = awaitItem()
            assertEquals(1, updatedList.size)
            assertEquals(name, updatedList.first().name)
        }
    }

    @Test
    fun `change plan name`() = runTest {
        val originalName = "Push Pull"
        val newName = "Push Pull Legs"
        val planId = repository.createPlan(name = originalName)
        repository.observePlan(planId).test {
            assertEquals(originalName, awaitItem()?.name)

            repository.changePlanName(planId = planId, newName = newName)

            assertEquals(newName, awaitItem()?.name)
        }
    }

    @Test
    fun `delete plan`() = runTest {
        val name = "Push Pull"
        val planId = repository.createPlan(name = name)
        repository.observeAllPlans().test {
            assertEquals(1, awaitItem().size)

            repository.deletePlan(planId = planId)

            assertEquals(0, awaitItem().size)
        }
    }

    @Test
    fun `add day`() = runTest {
        val planName = "Push Pull"
        val planId = repository.createPlan(planName)
        val dayPlanName = "Push 1"
        repository.observePlan(planId).test {
            assertEquals(emptyList(), awaitItem()!!.days)

            repository.addDay(planId = planId, name = dayPlanName)

            awaitItem()!!.let {
                assertEquals(1, it.days.size)
                assertEquals(dayPlanName, it.days.first().name)
            }
        }
    }

    @Test
    fun `change day name`() = runTest {
        val planName = "Push Pull"
        val planId = repository.createPlan(name = planName)
        val dayPlanName = "Push 1"
        val dayPlanId = repository.addDay(planId = planId, name = dayPlanName)
        val updatedDayPlanName = "Push 2"
        repository.observePlan(planId).test {
            assertEquals(dayPlanName, awaitItem()!!.days.first().name)

            repository.changeDayName(
                planId = planId,
                dayId = dayPlanId,
                newName = updatedDayPlanName
            )

            assertEquals(updatedDayPlanName, awaitItem()!!.days.first().name)
        }
    }

    @Test
    fun `delete day`() = runTest {
        val planName = "Push Pull"
        val planId = repository.createPlan(name = planName)
        val dayPlanName = "Push 1"
        val dayPlanId = repository.addDay(planId = planId, name = dayPlanName)
        repository.observePlan(planId).test {
            assertEquals(1, awaitItem()!!.days.size)

            repository.deleteDay(planId = planId, dayId = dayPlanId)

            assertEquals(0, awaitItem()!!.days.size)
        }
    }

    @Test
    fun `add exercise`() = runTest {
        val planName = "Push Pull"
        val planId = repository.createPlan(name = planName)
        val dayPlanName = "Push 1"
        val dayPlanId = repository.addDay(planId = planId, name = dayPlanName)
        val exerciseName = "Bench Press"
        val exerciseSets = Sets(regular = 3, drop = 0)
        val exerciseReps = Reps(10..12)
        repository.observePlan(planId).test {
            assertEquals(emptyList(), awaitItem()!!.days.first().exercises)

            repository.addExercise(
                planId = planId,
                dayId = dayPlanId,
                name = exerciseName,
                sets = exerciseSets,
                reps = exerciseReps
            )

            awaitItem()!!.let { plan ->
                plan.days.first().exercises.let { exercises ->
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
    fun `update exercise`() = runTest {
        val planName = "Push Pull"
        val planId = repository.createPlan(name = planName)
        val dayPlanName = "Push 1"
        val dayPlanId = repository.addDay(planId = planId, name = dayPlanName)
        val exerciseName = "Bench Press"
        val exerciseSets = Sets(regular = 3, drop = 0)
        val exerciseReps = Reps(10..12)
        val exerciseId = repository.addExercise(
            planId = planId,
            dayId = dayPlanId,
            name = exerciseName,
            sets = exerciseSets,
            reps = exerciseReps
        )
        val updatedExerciseName = "Incline Bench Press"
        val updatedSets = Sets(regular = 4, drop = 1)
        val updatedReps = Reps(8..10)
        repository.observePlan(planId).test {
            assertEquals(exerciseName, awaitItem()!!.days.first().exercises.first().name)

            repository.updateExercise(
                planId = planId,
                dayId = dayPlanId,
                exerciseId = exerciseId,
                newName = updatedExerciseName,
                newSets = updatedSets,
                newReps = updatedReps
            )

            assertEquals(updatedExerciseName, awaitItem()!!.days.first().exercises.first().name)
        }
    }

    @Test
    fun `delete exercise`() = runTest {
        val planName = "Push Pull"
        val planId = repository.createPlan(name = planName)
        val dayPlanName = "Push 1"
        val dayPlanId = repository.addDay(planId = planId, name = dayPlanName)
        val exerciseName = "Bench Press"
        val exerciseSets = Sets(regular = 3, drop = 0)
        val exerciseReps = Reps(10..12)
        val exerciseId = repository.addExercise(
            planId = planId,
            dayId = dayPlanId,
            name = exerciseName,
            sets = exerciseSets,
            reps = exerciseReps
        )
        repository.observePlan(planId).test {
            assertEquals(1, awaitItem()!!.days.first().exercises.size)

            repository.deleteExercise(
                planId = planId,
                dayId = dayPlanId,
                exerciseId = exerciseId
            )

            assertEquals(0, awaitItem()!!.days.first().exercises.size)
        }
    }
}
