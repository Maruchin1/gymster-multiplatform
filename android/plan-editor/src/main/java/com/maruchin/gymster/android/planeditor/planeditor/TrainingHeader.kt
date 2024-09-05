package com.maruchin.gymster.android.planeditor.planeditor

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.maruchin.gymster.android.ui.AppTheme
import com.maruchin.gymster.data.plans.model.PlannedTraining
import com.maruchin.gymster.data.plans.model.samplePlans

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LazyItemScope.TrainingHeader(
    training: PlannedTraining,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Column {
        TopAppBar(
            title = {
                Text(text = training.name)
            },
            actions = {
                Box {
                    var isMenuExpanded by rememberSaveable { mutableStateOf(false) }
                    IconButton(onClick = { isMenuExpanded = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = null)
                    }
                    DropdownMenu(
                        expanded = isMenuExpanded,
                        onDismissRequest = { isMenuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(text = "Change training name")
                            },
                            leadingIcon = {
                                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                            },
                            onClick = onEdit
                        )
                        DropdownMenuItem(
                            text = {
                                Text(text = "Delete training")
                            },
                            leadingIcon = {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                            },
                            onClick = onDelete
                        )
                    }
                }
            },
            windowInsets = WindowInsets(top = 0),
            modifier = Modifier.animateItem()
        )
        HorizontalDivider()
    }
}

@PreviewLightDark
@Composable
private fun TrainingHeaderPreview() {
    AppTheme {
        LazyColumn {
            item {
                TrainingHeader(
                    training = samplePlans.first().weeks.first().trainings.first(),
                    onEdit = {},
                    onDelete = {}
                )
            }
        }
    }
}
