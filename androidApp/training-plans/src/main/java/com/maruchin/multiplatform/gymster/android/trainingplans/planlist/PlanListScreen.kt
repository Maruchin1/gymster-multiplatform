package com.maruchin.multiplatform.gymster.android.trainingplans.planlist

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.maruchin.multiplatform.gymster.shared.feature.trainingplans.planlist.PlanListUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PlanListScreen(
    state: PlanListUiState,
    onBack: () -> Unit,
    onAddTrainingPlan: () -> Unit,
    onEditTrainingPlan: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Training Plans")
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTrainingPlan) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    ) { contentPadding ->
        Crossfade(targetState = state, modifier = Modifier.padding(contentPadding)) { targetState ->
            when (targetState) {
                PlanListUiState.Loading -> LoadingContent()
                PlanListUiState.Empty -> EmptyContent()
                is PlanListUiState.Loaded -> LoadedContent(
                    state = targetState,
                    onEditTrainingPlan = onEditTrainingPlan
                )
            }
        }
    }
}

@Composable
private fun LoadingContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun EmptyContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("No training plans yet", style = MaterialTheme.typography.titleSmall)
    }
}

@Composable
private fun LoadedContent(state: PlanListUiState.Loaded, onEditTrainingPlan: (String) -> Unit) {
    LazyColumn {
        items(state.plans) { trainingPlan ->
            Text(
                trainingPlan.name,
                modifier = Modifier
                    .clickable { onEditTrainingPlan(trainingPlan.id) }
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        }
    }
}
