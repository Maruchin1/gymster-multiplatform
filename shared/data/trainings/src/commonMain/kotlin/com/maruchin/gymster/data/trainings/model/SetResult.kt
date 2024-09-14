package com.maruchin.gymster.data.trainings.model

data class SetResult(val id: String, val type: Type, val weight: Double?, val reps: Int?) {

    val isComplete: Boolean
        get() = weight != null && reps != null

    enum class Type {
        REGULAR,
        DROP
    }

    companion object {

        fun emptyRegular() = SetResult(id = "", type = Type.REGULAR, weight = null, reps = null)

        fun emptyDrop() = SetResult(id = "", type = Type.DROP, weight = null, reps = null)
    }
}
