package com.maruchin.gymster.data.trainings.datasource

import com.maruchin.gymster.core.datastore.Settings
import kotlinx.coroutines.flow.Flow

internal class ActiveTrainingLocalDataSource(private val settings: Settings) {

    fun observeActiveTrainingBlockId(): Flow<String?> =
        settings.observeString(ACTIVE_TRAINING_BLOCK_ID)

    suspend fun setActiveTrainingBlock(trainingBlockId: String) {
        settings.setString(ACTIVE_TRAINING_BLOCK_ID, trainingBlockId)
    }

    companion object {

        private const val ACTIVE_TRAINING_BLOCK_ID = "active_training_block_id"
    }
}
