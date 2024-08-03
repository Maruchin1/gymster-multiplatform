package com.maruchin.gymster.android.trainings.trainingeditor

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.maruchin.gymster.android.ui.AppTheme
import com.maruchin.gymster.android.ui.LoadingContent
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets
import com.maruchin.gymster.data.trainings.model.Progress
import com.maruchin.gymster.data.trainings.model.sampleTrainings
import com.maruchin.gymster.feature.trainings.trainingeditor.TrainingEditorUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TrainingEditorScreen(
    state: TrainingEditorUiState,
    onBack: () -> Unit,
    onDeleteTraining: () -> Unit,
    onEditProgress: (exerciseId: String, progressIndex: Int) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            TopBar(
                state = state as? TrainingEditorUiState.Loaded,
                scrollBehavior = scrollBehavior,
                onBack = onBack,
                onDelete = onDeleteTraining
            )
        }
    ) { contentPadding ->
        AnimatedContent(
            targetState = state,
            contentKey = { it::class },
            label = "TrainingEditorScreenAnimatedContent",
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) { targetState ->
            when (targetState) {
                TrainingEditorUiState.Loading -> LoadingContent()
                is TrainingEditorUiState.Loaded -> LoadedContent(
                    state = targetState,
                    scrollBehavior = scrollBehavior,
                    onEditProgress = onEditProgress
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TopBar(
    state: TrainingEditorUiState.Loaded?,
    scrollBehavior: TopAppBarScrollBehavior,
    onBack: () -> Unit,
    onDelete: () -> Unit
) {
    var isConfirmingDeletion by rememberSaveable { mutableStateOf(false) }

    LargeTopAppBar(
        title = {
            Text(text = state?.training?.name.orEmpty())
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = { isConfirmingDeletion = true }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
            }
        },
        scrollBehavior = scrollBehavior
    )

    if (isConfirmingDeletion) {
        AlertDialog(
            onDismissRequest = { isConfirmingDeletion = false },
            confirmButton = {
                Button(
                    onClick = {
                        isConfirmingDeletion = false
                        onDelete()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { isConfirmingDeletion = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Cancel")
                }
            },
            icon = {
                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
            },
            title = {
                Text(text = "Delete training?")
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadedContent(
    state: TrainingEditorUiState.Loaded,
    scrollBehavior: TopAppBarScrollBehavior,
    onEditProgress: (exerciseId: String, progressIndex: Int) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
    ) {
        items(state.training.exercises) { exercise ->
            ExerciseItem(
                name = exercise.name,
                sets = exercise.sets,
                reps = exercise.reps,
                progress = exercise.progress,
                onEditProgress = { onEditProgress(exercise.id, it) }
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun ExerciseItem(
    name: String,
    sets: Sets,
    reps: Reps,
    progress: List<Progress?>,
    onEditProgress: (index: Int) -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Sets: $sets",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Reps: $reps",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            progress.forEachIndexed { index, progress ->
                AssistChip(
                    onClick = { onEditProgress(index) },
                    label = {
                        Text(text = progress.toString())
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
    }
}

@PreviewLightDark
@Composable
private fun TrainingEditorScreenPreview() {
    AppTheme {
        TrainingEditorScreen(
            state = TrainingEditorUiState.Loaded(training = sampleTrainings.first()),
            onBack = {},
            onDeleteTraining = {},
            onEditProgress = { _, _ -> }
        )
    }
}
