package com.maruchin.gymster.data.trainings.model

data class Progress(val weight: Double? = null, val reps: Int? = null) {

    constructor(progress: Pair<Double?, Int?>) : this(progress.first, progress.second)

    val isComplete: Boolean
        get() = weight != null && reps != null

    override fun toString(): String = "${weight ?: "__"} x ${reps ?: "__"} "
}
