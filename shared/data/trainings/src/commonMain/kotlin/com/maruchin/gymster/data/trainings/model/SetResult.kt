package com.maruchin.gymster.data.trainings.model

import com.maruchin.gymster.data.plans.model.Reps

data class SetResult(val id: String, val type: Type, val weight: Double?, val reps: Int?) {

    val isComplete: Boolean
        get() = weight != null && reps != null

    fun isTooMuch(expectedReps: Reps): Boolean = this.reps != null && this.reps < expectedReps.min

    fun isTooLittle(expectedReps: Reps): Boolean =
        this.reps != null && this.reps >= expectedReps.max

    enum class Type {
        REGULAR,
        DROP
    }

    companion object {

        fun emptyRegular() = SetResult(id = "", type = Type.REGULAR, weight = null, reps = null)

        fun emptyDrop() = SetResult(id = "", type = Type.DROP, weight = null, reps = null)
    }
}
