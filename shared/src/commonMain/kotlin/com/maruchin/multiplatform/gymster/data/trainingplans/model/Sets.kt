package com.maruchin.multiplatform.gymster.data.trainingplans.model

data class Sets(val regular: Int, val drop: Int = 0) {
    init {
        require(regular >= 1) { "regularSets must be greater or equal to 0" }
        require(drop >= 0) { "superSets must be greater or equal to 0" }
    }
}
