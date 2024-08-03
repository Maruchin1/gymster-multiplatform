package com.maruchin.gymster.android.plans.planform

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
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
import com.maruchin.gymster.data.plans.model.Plan

@Composable
internal fun PlanFormDialog(plan: Plan?, onClose: () -> Unit, onSave: (name: String) -> Unit) {
    var name by rememberSaveable(plan) { mutableStateOf(plan?.name.orEmpty()) }

    AlertDialog(
        onDismissRequest = onClose,
        icon = {
            Icon(Icons.Default.CalendarMonth, contentDescription = null)
        },
        title = {
            Text("New plan")
        },
        text = {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Plan name") }
            )
        },
        dismissButton = {
            TextButton(onClick = onClose) {
                Text("Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = { onSave(name) }) {
                Text("Save")
            }
        }
    )
}

@PreviewLightDark
@Composable
private fun PlanFormDialogPreview() {
    AppTheme {
        PlanFormDialog(
            plan = null,
            onClose = {},
            onSave = {}
        )
    }
}
