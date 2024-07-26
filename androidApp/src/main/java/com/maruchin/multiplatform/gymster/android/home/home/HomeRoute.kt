package com.maruchin.multiplatform.gymster.android.home.home

import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable

@Serializable
internal data object HomeRoute

@Composable
internal fun HomeRoute(onOpenTrainingPlans: () -> Unit) {
    HomeScreen(
        onOpenTrainingPlans = onOpenTrainingPlans
    )
}
