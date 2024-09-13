package com.maruchin.gymster.data.trainings.model

// TODO Rename to SetResult
data class SetProgress(val id: String, val type: Type, val progress: Progress?) {

    val isComplete: Boolean
        get() = progress != null

    enum class Type {
        REGULAR,
        DROP
    }
}
