package com.maruchin.gymster.data.trainings.model

data class Progress(val weight: Double, val reps: Int) {

    constructor(progress: Pair<Double, Int>) : this(progress.first, progress.second)

    override fun toString(): String = "$weight x $reps "
}
