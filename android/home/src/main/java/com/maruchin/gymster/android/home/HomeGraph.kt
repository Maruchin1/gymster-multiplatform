package com.maruchin.gymster.android.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import kotlinx.serialization.Serializable

@Serializable
object HomeGraph

fun NavGraphBuilder.homeGraph(
    onOpenPlans: () -> Unit,
    onOpenTrainings: () -> Unit,
    onOpenTrainingBlock: (trainingBlockId: String) -> Unit,
    onOpenTraining: (trainingBlockId: String, weekIndex: Int, trainingIndex: Int) -> Unit
) {
    navigation<HomeGraph>(startDestination = HomeRoute) {
        homeScreen(
            onOpenPlans = onOpenPlans,
            onOpenTrainings = onOpenTrainings,
            onOpenTrainingBlock = onOpenTrainingBlock,
            onOpenTraining = onOpenTraining
        )
    }
}
