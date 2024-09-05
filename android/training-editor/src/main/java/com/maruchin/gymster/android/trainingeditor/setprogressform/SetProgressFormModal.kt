package com.maruchin.gymster.android.trainingeditor.setprogressform

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.maruchin.gymster.android.ui.AppTheme
import com.maruchin.gymster.data.trainings.model.Progress
import com.maruchin.gymster.data.trainings.model.SetProgress
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SetProgressFormModal(
    setProgress: SetProgress,
    onDismiss: () -> Unit,
    onSave: (Progress) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
    ) {
        SetProgressFormModal(
            setProgress = setProgress,
            onSave = {
                onSave(it)
                scope.launch {
                    sheetState.hide()
                    onDismiss()
                }
            }
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SetProgressFormModal(setProgress: SetProgress, onSave: (Progress) -> Unit) {
    var weight by remember(setProgress) {
        mutableStateOf(setProgress.progress?.weight?.toString().orEmpty())
    }
    var reps by remember(setProgress) {
        mutableStateOf(setProgress.progress?.reps?.toString().orEmpty())
    }

    Column(
        modifier = Modifier
            .imePadding()
            .navigationBarsPadding()
    ) {
        TopAppBar(
            title = {
                Text(
                    text = when (setProgress.type) {
                        SetProgress.Type.REGULAR -> "Regular set"
                        SetProgress.Type.DROP -> "Drop set"
                    }
                )
            }
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            OutlinedTextField(
                value = weight,
                onValueChange = { weight = it },
                label = {
                    Text(text = "Weight")
                },
                suffix = {
                    Text(text = "kg")
                },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = reps,
                onValueChange = { reps = it },
                label = {
                    Text(text = "Reps")
                },
                modifier = Modifier.weight(1f)
            )
        }
        Button(
            onClick = {
                val updatedProgress = Progress(weight = weight.toDouble(), reps = reps.toInt())
                onSave(updatedProgress)
            },
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
private fun SetProgressFormModal_NoProgressPreview() {
    AppTheme {
        Surface {
            SetProgressFormModal(
                setProgress = SetProgress(
                    id = "1",
                    type = SetProgress.Type.REGULAR,
                    progress = null
                ),
                onSave = {}
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun SetProgressFormModel_EditProgressPreview() {
    AppTheme {
        Surface {
            SetProgressFormModal(
                setProgress = SetProgress(
                    id = "1",
                    type = SetProgress.Type.REGULAR,
                    progress = Progress(weight = 50.0, reps = 10)
                ),
                onSave = {}
            )
        }
    }
}
