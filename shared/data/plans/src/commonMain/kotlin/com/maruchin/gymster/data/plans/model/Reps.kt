package com.maruchin.gymster.data.plans.model

data class Reps(val min: Int, val max: Int) {
    constructor(range: IntRange) : this(range.first, range.last)

    init {
        require(min >= 0) { "min must be greater or equal to 0" }
        require(max >= 0) { "max must be greater or equal to 0" }
        require(min <= max) { "min must be less or equal to max" }
    }

    val range: IntRange = min..max

    override fun toString(): String = "$min-$max"
}
