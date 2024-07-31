package com.maruchin.gymster.android.plans.planlist

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.maruchin.gymster.android.ui.AppTheme
import com.maruchin.gymster.android.ui.EmptyContent
import com.maruchin.gymster.android.ui.LoadingContent
import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.plans.model.samplePlans
import com.maruchin.gymster.feature.plans.planlist.PlanListUiState

@Composable
internal fun PlanListScreen(
    state: PlanListUiState,
    onAddTrainingPlan: () -> Unit,
    onEditTrainingPlan: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopBar()
        },
        floatingActionButton = {
            AnimatedVisibility(visible = state !is PlanListUiState.Loading) {
                FloatingActionButton(onClick = onAddTrainingPlan) {
                    Icon(Icons.Default.Add, contentDescription = null)
                }
            }
        }
    ) { contentPadding ->
        AnimatedContent(
            targetState = state,
            contentKey = { it::class },
            label = "PlanListScreenAnimatedContent",
            modifier = Modifier.padding(contentPadding)
        ) { targetState ->
            when (targetState) {
                PlanListUiState.Loading -> LoadingContent()
                PlanListUiState.Empty -> EmptyContent(text = "No training plans yet")
                is PlanListUiState.Loaded -> LoadedContent(
                    plans = targetState.plans,
                    onEditTrainingPlan = onEditTrainingPlan
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text("Training plans")
        }
    )
}

@Composable
private fun LoadedContent(plans: List<Plan>, onEditTrainingPlan: (String) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(plans) { trainingPlan ->
            TrainingPlanItem(
                name = trainingPlan.name,
                onClick = { onEditTrainingPlan(trainingPlan.id) }
            )
        }
    }
}

@Composable
private fun TrainingPlanItem(name: String, onClick: () -> Unit) {
    ElevatedCard(onClick = onClick) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .clickable { onClick() }
                .padding(16.dp)
                .fillMaxWidth()
        )
    }
}

@PreviewLightDark
@Composable
private fun PlanListScreen_LoadedPreview() {
    AppTheme {
        PlanListScreen(
            state = PlanListUiState.Loaded(plans = samplePlans),
            onAddTrainingPlan = {},
            onEditTrainingPlan = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun PlanListScreen_LoadingPreview() {
    AppTheme {
        PlanListScreen(
            state = PlanListUiState.Loading,
            onAddTrainingPlan = {},
            onEditTrainingPlan = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun PlanListScreen_EmptyPreview() {
    AppTheme {
        PlanListScreen(
            state = PlanListUiState.Empty,
            onAddTrainingPlan = {},
            onEditTrainingPlan = {}
        )
    }
}
