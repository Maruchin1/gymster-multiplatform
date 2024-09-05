package com.maruchin.gymster.android.planeditor.planeditor

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DragHandle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.maruchin.gymster.android.ui.AppTheme
import com.maruchin.gymster.data.plans.model.PlannedExercise
import com.maruchin.gymster.data.plans.model.samplePlans
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.ReorderableLazyListState
import sh.calvin.reorderable.rememberReorderableLazyListState

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun LazyItemScope.PlannedExerciseItem(
    exercise: PlannedExercise,
    reorderableLazyListState: ReorderableLazyListState,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onDragStopped: () -> Unit
) {
    ReorderableItem(state = reorderableLazyListState, key = exercise.id) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .animateItem()
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier.padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {},
                    modifier = Modifier.draggableHandle(onDragStopped = onDragStopped)
                ) {
                    Icon(imageVector = Icons.Default.DragHandle, contentDescription = null)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = exercise.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Sets: ${exercise.sets}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Reps: ${exercise.reps}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                var isMenuExpanded by rememberSaveable { mutableStateOf(false) }
                Box {
                    IconButton(onClick = { isMenuExpanded = true }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                    }
                    DropdownMenu(
                        expanded = isMenuExpanded,
                        onDismissRequest = { isMenuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(text = "Edit exercise")
                            },
                            leadingIcon = {
                                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                            },
                            onClick = onEdit
                        )
                        DropdownMenuItem(
                            text = {
                                Text(text = "Delete exercise")
                            },
                            leadingIcon = {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                            },
                            onClick = onDelete
                        )
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PlannedExerciseItemPreview() {
    AppTheme {
        LazyColumn {
            item {
                PlannedExerciseItem(
                    exercise = samplePlans.first().weeks.first().trainings.first().exercises.first(),
                    reorderableLazyListState = rememberReorderableLazyListState(
                        lazyListState = rememberLazyListState(),
                        onMove = { _, _ -> }
                    ),
                    onEdit = {},
                    onDelete = {},
                    onDragStopped = {}
                )
            }
        }
    }
}
