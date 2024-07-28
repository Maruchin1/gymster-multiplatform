package com.maruchin.multiplatform.gymster.android.trainingplans.planform

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.maruchin.gymster.android.ui.AppTheme
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.model.TrainingPlan

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PlanFormDialog(
    plan: TrainingPlan?,
    onClose: () -> Unit,
    onSave: (name: String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Training plan")
                },
                navigationIcon = {
                    IconButton(onClick = onClose) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }
            )
        },
        modifier = Modifier.imePadding()
    ) { contentPadding ->
        val focusRequester = remember { FocusRequester() }
        var name by rememberSaveable(plan) { mutableStateOf(plan?.name.orEmpty()) }

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        Column(
            modifier = Modifier
                .padding(contentPadding)
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            TextField(
                value = name,
                onValueChange = { name = it },
                label = {
                    Text("Plan name")
                },
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    onSave(name)
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
private fun PlanFormDialogPreview() {
    AppTheme {
        PlanFormDialog(
            plan = null,
            onClose = {},
            onSave = {}
        )
    }
}
