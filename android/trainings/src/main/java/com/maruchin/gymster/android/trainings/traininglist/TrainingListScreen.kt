package com.maruchin.gymster.android.trainings.traininglist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.maruchin.gymster.android.ui.AppTheme
import com.maruchin.gymster.android.ui.EmptyContent
import com.maruchin.gymster.android.ui.formatFull
import com.maruchin.gymster.android.ui.formatMedium
import com.maruchin.gymster.data.trainings.model.sampleTrainingWeeks
import com.maruchin.gymster.shared.feature.trainings.traininglist.TrainingListUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TrainingListScreen(
    state: TrainingListUiState,
    onBack: () -> Unit,
    onStartTraining: () -> Unit,
    onOpenTraining: (String) -> Unit
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        topBar = {
            TopBar(onBack = onBack, topAppBarScrollBehavior = topAppBarScrollBehavior)
        },
        floatingActionButton = {
            StartNewTrainingButton(onClick = onStartTraining)
        }
    ) { innerPadding ->
        if (state.trainings.isEmpty()) {
            EmptyContent(
                text = "Start your first training",
                modifier = Modifier.padding(innerPadding)
            )
        } else {
            LoadedContent(
                innerPadding = innerPadding,
                state = state,
                topAppBarScrollBehavior = topAppBarScrollBehavior,
                onOpenTraining = onOpenTraining
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopBar(onBack: () -> Unit, topAppBarScrollBehavior: TopAppBarScrollBehavior) {
    LargeTopAppBar(
        title = {
            Text(text = "Trainings")
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = null
                )
            }
        },
        scrollBehavior = topAppBarScrollBehavior
    )
}

@Composable
private fun StartNewTrainingButton(onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        icon = {
            Icon(imageVector = Icons.Outlined.FitnessCenter, contentDescription = null)
        },
        text = {
            Text(text = "Start new training")
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun LoadedContent(
    innerPadding: PaddingValues,
    state: TrainingListUiState,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    onOpenTraining: (trainingId: String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(innerPadding)
            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        state.trainings.forEach { trainingWeek ->
            stickyHeader {
                Text(
                    text = "${trainingWeek.startDate.formatMedium()} - ${trainingWeek.endDate.formatMedium()}",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            items(trainingWeek.trainings, key = { it.id }) { training ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItem(),
                    onClick = { onOpenTraining(training.id) }
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = training.date.formatFull(),
                            style = MaterialTheme.typography.labelLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = training.name, style = MaterialTheme.typography.titleLarge)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = training.planName, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun TrainingListUiState_EmptyPreview() {
    AppTheme {
        TrainingListScreen(
            state = TrainingListUiState(),
            onBack = {},
            onOpenTraining = {},
            onStartTraining = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun TrainingListScreen_LoadedPreview() {
    AppTheme {
        TrainingListScreen(
            state = TrainingListUiState(sampleTrainingWeeks),
            onBack = {},
            onOpenTraining = {},
            onStartTraining = {}
        )
    }
}
