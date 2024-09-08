package com.maruchin.gymster.android.planeditor.trainingform

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.maruchin.gymster.android.ui.AppTheme
import com.maruchin.gymster.data.plans.model.PlannedTraining
import com.maruchin.gymster.data.plans.model.samplePlans
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TrainingFormModal(
    training: PlannedTraining?,
    onDismiss: () -> Unit,
    onSave: (newName: String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    ModalBottomSheet(onDismissRequest = onDismiss, sheetState = sheetState) {
        TrainingFormContent(
            training = training,
            onSave = { newName ->
                scope.launch {
                    onSave(newName)
                    sheetState.hide()
                    onDismiss()
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TrainingFormContent(training: PlannedTraining?, onSave: (newName: String) -> Unit) {
    val trainingNameFocus = remember { FocusRequester() }
    var trainingName by remember(training) { mutableStateOf(training?.name.orEmpty()) }

    LaunchedEffect(Unit) {
        delay(300)
        trainingNameFocus.requestFocus()
    }

    Column(modifier = Modifier.background(BottomSheetDefaults.ContainerColor)) {
        TopAppBar(
            title = {
                Text(text = "Training")
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = BottomSheetDefaults.ContainerColor
            ),
            windowInsets = WindowInsets(0)
        )
        OutlinedTextField(
            value = trainingName,
            onValueChange = { trainingName = it },
            label = {
                Text(text = "Training name")
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(trainingNameFocus)
                .padding(horizontal = 16.dp, vertical = 24.dp)
        )
        Button(
            onClick = { onSave(trainingName) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Save")
        }
    }
}

@PreviewLightDark
@Composable
private fun TrainingFormModalPreview() {
    AppTheme {
        TrainingFormContent(
            training = samplePlans.first().trainings.first(),
            onSave = {}
        )
    }
}
