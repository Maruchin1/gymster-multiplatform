package com.maruchin.gymster.android.trainings.progresseditor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.maruchin.gymster.android.ui.AppTheme
import com.maruchin.gymster.data.trainings.model.Progress
import com.maruchin.gymster.data.trainings.model.TrainingExercise
import com.maruchin.gymster.data.trainings.model.sampleTrainings
import com.maruchin.gymster.feature.trainings.progresseditor.ProgressEditorUiState

@Composable
internal fun ProgressEditorDialog(
    state: ProgressEditorUiState,
    onClose: () -> Unit,
    onSave: (Progress) -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(onClose = onClose)
        },
        modifier = Modifier.imePadding()
    ) { contentPadding ->
        val loadedState = state as? ProgressEditorUiState.Loaded
        val initialWeight = loadedState?.progress?.weight?.toString().orEmpty()
        val initialReps = loadedState?.progress?.reps?.toString().orEmpty()

        var weight by rememberSaveable(initialWeight) { mutableStateOf(initialWeight) }
        var reps by rememberSaveable(initialReps) { mutableStateOf(initialReps) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding)
        ) {
            ExerciseInfo(exercise = loadedState?.exercise)
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 32.dp)
                    .weight(1f)
            ) {
                TextField(
                    value = weight,
                    onValueChange = { weight = it },
                    label = {
                        Text(text = "Weight")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.weight(1f)
                )
                TextField(
                    value = reps,
                    onValueChange = { reps = it },
                    label = {
                        Text(text = "Reps")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.weight(1f)
                )
            }
            Button(
                onClick = {
                    val progress = Progress(
                        weight = weight.toDoubleOrNull(),
                        reps = reps.toIntOrNull()
                    )
                    onSave(progress)
                },
                enabled = weight.isNotBlank() && reps.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Save")
            }
        }
    }
}

@Composable
private fun ExerciseInfo(exercise: TrainingExercise?) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = exercise?.name.orEmpty(),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Sets: ${exercise?.sets ?: 0}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Reps: ${exercise?.reps ?: 0}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(onClose: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = "Progress")
        },
        navigationIcon = {
            IconButton(onClick = onClose) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
        }
    )
}

@PreviewLightDark
@Composable
private fun ProgressEditorDialogPreview() {
    AppTheme {
        ProgressEditorDialog(
            state = ProgressEditorUiState.Loaded(
                exercise = sampleTrainings.first().exercises.first(),
                progress = sampleTrainings.first().exercises.first().progress.first()
            ),
            onClose = {},
            onSave = {}
        )
    }
}
