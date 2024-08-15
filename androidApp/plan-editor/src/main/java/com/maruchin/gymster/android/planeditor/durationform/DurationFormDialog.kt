package com.maruchin.gymster.android.planeditor.durationform

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.maruchin.gymster.android.ui.AppTheme
import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.plans.model.samplePlans

@Composable
internal fun DurationDialog(plan: Plan?, onDismiss: () -> Unit, onSave: (Int) -> Unit) {
    var duration by rememberSaveable(plan) {
        mutableStateOf(plan?.weeksDuration?.toString().orEmpty())
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
        },
        title = {
            Text(text = "Plan duration")
        },
        text = {
            OutlinedTextField(
                value = duration,
                onValueChange = { duration = it },
                suffix = {
                    Text(text = "weeks", modifier = Modifier.padding(start = 12.dp))
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End)
            )
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = { onSave(duration.toInt()) }) {
                Text(text = "Save")
            }
        }
    )
}

@PreviewLightDark
@Composable
private fun DurationDialogPreview() {
    AppTheme {
        DurationDialog(plan = samplePlans.first(), onDismiss = {}, onSave = {})
    }
}
