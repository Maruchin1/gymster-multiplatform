package com.maruchin.gymster.android.planlist.newplan

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ListAlt
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

@Composable
internal fun NewPlanDialog(onDismiss: () -> Unit, onConfirm: (planName: String) -> Unit) {
    var planName by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = { onConfirm(planName) }
            ) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        },
        icon = {
            Icon(imageVector = Icons.AutoMirrored.Default.ListAlt, contentDescription = null)
        },
        title = {
            Text(text = "New Plan")
        },
        text = {
            OutlinedTextField(
                value = planName,
                onValueChange = { planName = it },
                label = {
                    Text("Plan name")
                }
            )
        }
    )
}

@PreviewLightDark
@Composable
private fun NewPlanDialogPreview() {
    AppTheme {
        NewPlanDialog(onDismiss = {}, onConfirm = {})
    }
}
