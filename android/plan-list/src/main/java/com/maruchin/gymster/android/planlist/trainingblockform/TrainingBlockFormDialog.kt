package com.maruchin.gymster.android.planlist.trainingblockform

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.maruchin.gymster.android.ui.AppTheme
import com.maruchin.gymster.core.clock.toLocalDate
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TrainingBlockFormDialog(
    onDismiss: () -> Unit,
    onCreateTrainingBlock: (startDate: LocalDate) -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Clock.System.now().toEpochMilliseconds()
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    val selectedDateMillis = checkNotNull(datePickerState.selectedDateMillis)
                    val selectedDate = selectedDateMillis.toLocalDate()
                    onCreateTrainingBlock(selectedDate)
                }
            ) {
                Text(text = "Create")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            title = {
                Text(
                    text = "Select start date",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(12.dp)
                )
            },
            headline = {
                Text(text = "New training block", modifier = Modifier.padding(12.dp))
            },
            showModeToggle = false
        )
    }
}

@PreviewLightDark
@Composable
private fun TrainingBlockFormDialogPreview() {
    AppTheme {
        TrainingBlockFormDialog(
            onDismiss = {},
            onCreateTrainingBlock = {}
        )
    }
}
