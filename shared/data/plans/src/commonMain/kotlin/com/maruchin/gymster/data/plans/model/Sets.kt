package com.maruchin.gymster.data.plans.model

data class Sets(val regular: Int, val drop: Int = 0) {
    init {
        require(regular >= 1) { "regularSets must be greater or equal to 0" }
        require(drop >= 0) { "superSets must be greater or equal to 0" }
    }

    val total: Int
        get() = regular + drop

    override fun toString(): String {
        val dropSigns = (1..drop).joinToString(separator = "") { "+" }
        return "$regular$dropSigns"
    }
}
