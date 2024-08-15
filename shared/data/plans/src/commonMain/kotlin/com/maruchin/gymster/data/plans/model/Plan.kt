package com.maruchin.gymster.data.plans.model

data class Plan(
    val id: String,
    val name: String,
    val weeksDuration: Int,
    val trainings: List<PlannedTraining>
) {

    init {
        require(weeksDuration > 0) { "weeksDuration must be greater than 0" }
    }

    fun getTraining(trainingId: String) = trainings.first { it.id == trainingId }

    fun getExercise(exerciseId: String) =
        trainings.flatMap { it.exercises }.first { it.id == exerciseId }

    fun changeExercisesOrder(fromId: String, toId: String) = copy(
        trainings = trainings.map { day ->
            if (day.exercises.find { it.id == fromId } != null) {
                day.changeExercisesOrder(fromId, toId)
            } else {
                day
            }
        }
    )

    companion object {

        const val DEFAULT_WEEKS_DURATION = 8
    }
}
