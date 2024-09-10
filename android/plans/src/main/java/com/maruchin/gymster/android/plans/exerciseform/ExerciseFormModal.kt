package com.maruchin.gymster.android.plans.exerciseform

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.maruchin.gymster.android.ui.AppTheme
import com.maruchin.gymster.data.plans.model.PlannedExercise
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets
import com.maruchin.gymster.data.plans.model.samplePlans
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ExerciseFormModal(
    exercise: PlannedExercise?,
    onSave: (name: String, sets: Sets, reps: Reps) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    ModalBottomSheet(onDismissRequest = onDismiss, sheetState = sheetState) {
        ExerciseFormContent(
            exercise = exercise,
            onSave = { name, sets, reps ->
                scope.launch {
                    onSave(name, sets, reps)
                    sheetState.hide()
                    onDismiss()
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExerciseFormContent(
    exercise: PlannedExercise?,
    onSave: (name: String, sets: Sets, reps: Reps) -> Unit
) {
    val exerciseNameFocus = remember { FocusRequester() }
    var exerciseName by rememberSaveable(exercise) {
        mutableStateOf(exercise?.name.orEmpty())
    }
    var regularSets by rememberSaveable(exercise) {
        mutableStateOf(exercise?.sets?.regular?.toString().orEmpty())
    }
    var dropSets by rememberSaveable(exercise) {
        mutableStateOf(exercise?.sets?.drop?.toString().orEmpty())
    }
    var minReps by rememberSaveable(exercise) {
        mutableStateOf(exercise?.reps?.min?.toString().orEmpty())
    }
    var maxReps by rememberSaveable(exercise) {
        mutableStateOf(exercise?.reps?.max?.toString().orEmpty())
    }

    val isValid by remember {
        derivedStateOf {
            arrayOf(exerciseName, regularSets, dropSets, minReps, maxReps).all { it.isNotBlank() }
        }
    }

    LaunchedEffect(Unit) {
        delay(300)
        exerciseNameFocus.requestFocus()
    }

    Column(modifier = Modifier.background(BottomSheetDefaults.ContainerColor)) {
        TopAppBar(
            title = {
                Text("Exercise")
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = BottomSheetDefaults.ContainerColor
            ),
            windowInsets = WindowInsets(0)
        )
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            value = exerciseName,
            onValueChange = { exerciseName = it },
            label = {
                Text("Exercise name")
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(exerciseNameFocus)
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            OutlinedTextField(
                value = regularSets,
                onValueChange = { regularSets = it },
                label = {
                    Text("Regular sets")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = dropSets,
                onValueChange = { dropSets = it },
                label = {
                    Text("Drop sets")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            OutlinedTextField(
                value = minReps,
                onValueChange = { minReps = it },
                label = {
                    Text("Min reps")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = maxReps,
                onValueChange = { maxReps = it },
                label = {
                    Text("Max reps")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                val sets = Sets(regular = regularSets.toInt(), drop = dropSets.toInt())
                val reps = Reps(min = minReps.toInt(), max = maxReps.toInt())
                onSave(exerciseName, sets, reps)
            },
            enabled = isValid,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Save")
        }
    }
}

@PreviewLightDark
@Composable
private fun ExerciseFormModalPreview() {
    AppTheme {
        ExerciseFormContent(
            exercise = samplePlans.first().trainings.first().exercises.first(),
            onSave = { _, _, _ -> }
        )
    }
}
