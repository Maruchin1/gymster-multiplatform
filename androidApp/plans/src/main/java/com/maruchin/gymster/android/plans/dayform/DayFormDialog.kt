package com.maruchin.gymster.android.plans.dayform

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.maruchin.gymster.android.ui.AppTheme
import com.maruchin.gymster.data.plans.model.PlannedTraining

@Composable
internal fun DayFormDialog(
    plannedTraining: PlannedTraining?,
    onClose: () -> Unit,
    onSave: (name: String) -> Unit
) {
    var name by rememberSaveable(plannedTraining) {
        mutableStateOf(plannedTraining?.name.orEmpty())
    }

    AlertDialog(
        onDismissRequest = onClose,
        icon = {
            Icon(imageVector = Icons.Default.EditCalendar, contentDescription = null)
        },
        title = {
            Text(text = "Add training")
        },
        text = {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = {
                    Text("Training name")
                }
            )
        },
        confirmButton = {
            TextButton(onClick = { onSave(name) }) {
                Text(text = "Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onClose) {
                Text(text = "Cancel")
            }
        }
    )
}

@PreviewLightDark
@Composable
private fun DayFormDialogPreview() {
    AppTheme {
        DayFormDialog(
            plannedTraining = null,
            onClose = {},
            onSave = {}
        )
    }
}
