package com.maruchin.multiplatform.gymster.android.trainingplans.exerciseform

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.maruchin.gymster.android.ui.AppTheme
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.model.Reps
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.model.Sets
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.model.TrainingPlanExercise

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ExerciseFormDialog(
    exercise: TrainingPlanExercise?,
    onClose: () -> Unit,
    onSave: (name: String, sets: Sets, reps: Reps) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Exercise")
                },
                navigationIcon = {
                    IconButton(onClick = onClose) {
                        Icon(Icons.Default.Close, contentDescription = null)
                    }
                }
            )
        },
        modifier = Modifier.imePadding()
    ) { contentPadding ->
        val focusRequester = remember { FocusRequester() }
        var name by rememberSaveable(exercise?.name) {
            mutableStateOf(exercise?.name.orEmpty())
        }
        var regularSets by rememberSaveable(exercise?.sets?.regular) {
            mutableStateOf(exercise?.sets?.regular?.toString().orEmpty())
        }
        var dropSets by rememberSaveable(exercise?.sets?.drop) {
            mutableStateOf(exercise?.sets?.drop?.toString().orEmpty())
        }
        var minReps by rememberSaveable(exercise?.reps?.min) {
            mutableStateOf(exercise?.reps?.min?.toString().orEmpty())
        }
        var maxReps by rememberSaveable(exercise?.reps?.max) {
            mutableStateOf(exercise?.reps?.max?.toString().orEmpty())
        }

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding)
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            TextField(
                value = name,
                onValueChange = { name = it },
                label = {
                    Text("Exercise name")
                },
                singleLine = true,
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Row(modifier = Modifier.padding(16.dp)) {
                TextField(
                    value = regularSets,
                    onValueChange = { regularSets = it },
                    label = {
                        Text("Regular sets")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    ),
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                TextField(
                    value = dropSets,
                    onValueChange = { dropSets = it },
                    label = {
                        Text("Drop sets")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    ),
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
            }
            Row(modifier = Modifier.padding(16.dp)) {
                TextField(
                    value = minReps,
                    onValueChange = { minReps = it },
                    label = {
                        Text("Min reps")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    ),
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                TextField(
                    value = maxReps,
                    onValueChange = { maxReps = it },
                    label = {
                        Text("Max sets")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    ),
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    val sets = Sets(
                        regular = regularSets.toIntOrNull() ?: 0,
                        drop = dropSets.toIntOrNull() ?: 0
                    )
                    val reps = Reps(
                        min = minReps.toIntOrNull() ?: 0,
                        max = maxReps.toIntOrNull() ?: 0
                    )
                    onSave(name, sets, reps)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Save")
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun ExerciseFormDialogPreview() {
    AppTheme {
        ExerciseFormDialog(
            exercise = null,
            onClose = {},
            onSave = { _, _, _ -> }
        )
    }
}
