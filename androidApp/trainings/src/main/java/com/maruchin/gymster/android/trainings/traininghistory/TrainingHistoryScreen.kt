package com.maruchin.gymster.android.trainings.traininghistory

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.maruchin.gymster.android.ui.AppTheme
import com.maruchin.gymster.android.ui.EmptyContent
import com.maruchin.gymster.android.ui.LoadingContent
import com.maruchin.gymster.android.ui.formatMedium
import com.maruchin.gymster.data.trainings.model.Training
import com.maruchin.gymster.data.trainings.model.sampleTrainings
import com.maruchin.gymster.feature.trainings.traininghistory.TrainingHistoryUiState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate

@Composable
internal fun TrainingHistoryScreen(
    state: TrainingHistoryUiState,
    onCreateTraining: () -> Unit,
    onOpenTraining: (trainingId: String) -> Unit
) {
    Scaffold(
        topBar = {
            TopBar()
        },
        floatingActionButton = {
            CreateTrainingButton(onClick = onCreateTraining)
        }
    ) { contentPadding ->
        AnimatedContent(
            targetState = state,
            label = "TrainingHistoryScreenAnimatedContent",
            modifier = Modifier.padding(contentPadding)
        ) { targetState ->
            when (targetState) {
                TrainingHistoryUiState.Loading -> LoadingContent()
                TrainingHistoryUiState.Empty -> EmptyContent(text = "No trainings yet")
                is TrainingHistoryUiState.Loaded -> LoadedContent(
                    trainings = targetState.trainings,
                    onOpenTraining = onOpenTraining
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(text = "Training history")
        }
    )
}

@Composable
private fun CreateTrainingButton(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Icon(imageVector = Icons.Default.FitnessCenter, contentDescription = null)
    }
}

@Composable
private fun LoadedContent(trainings: List<Training>, onOpenTraining: (trainingId: String) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(trainings) { training ->
            TrainingItem(
                name = training.name,
                planName = training.planName,
                date = training.date,
                onClick = { onOpenTraining(training.id) }
            )
        }
    }
}

@Composable
private fun TrainingItem(name: String, planName: String, date: LocalDate, onClick: () -> Unit) {
    ElevatedCard(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = date.toJavaLocalDate().formatMedium(),
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Text(text = planName, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@PreviewLightDark
@Composable
private fun TrainingHistoryScreen_LoadedPreview() {
    AppTheme {
        TrainingHistoryScreen(
            state = TrainingHistoryUiState.Loaded(sampleTrainings),
            onCreateTraining = {},
            onOpenTraining = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun TrainingHistoryScreen_EmptyPreview() {
    AppTheme {
        TrainingHistoryScreen(
            state = TrainingHistoryUiState.Empty,
            onCreateTraining = {},
            onOpenTraining = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun TrainingHistoryScreen_LoadingPreview() {
    AppTheme {
        TrainingHistoryScreen(
            state = TrainingHistoryUiState.Loading,
            onCreateTraining = {},
            onOpenTraining = {}
        )
    }
}
