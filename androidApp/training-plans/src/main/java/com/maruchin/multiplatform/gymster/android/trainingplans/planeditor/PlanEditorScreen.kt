package com.maruchin.multiplatform.gymster.android.trainingplans.planeditor

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DragHandle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.model.Reps
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.model.Sets
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.model.TrainingPlan
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.model.changeExercisesOrder
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.model.sampleTrainingPlans
import com.maruchin.multiplatform.gymster.shared.feature.trainingplans.planeditor.PlanEditorUiState
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.ReorderableLazyListState
import sh.calvin.reorderable.rememberReorderableLazyListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PlanEditorScreen(
    state: PlanEditorUiState,
    onBack: () -> Unit,
    onEditName: () -> Unit,
    onDeletePlan: () -> Unit,
    onAddDay: () -> Unit,
    onEditDay: (dayId: String) -> Unit,
    onDeleteDay: (dayId: String) -> Unit,
    onAddExercise: (dayId: String) -> Unit,
    onEditExercise: (dayId: String, exerciseId: String) -> Unit,
    onDeleteExercise: (dayId: String, exerciseId: String) -> Unit,
    onReorderExercises: (dayId: String, exercisesIds: List<String>) -> Unit
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            TopBar(
                state = state as? PlanEditorUiState.Loaded,
                topAppBarScrollBehavior = topAppBarScrollBehavior,
                onBack = onBack,
                onEdit = onEditName,
                onDelete = onDeletePlan
            )
        }
    ) { contentPadding ->
        AnimatedContent(
            targetState = state,
            contentKey = { it::class },
            label = "PlanEditorScreenAnimatedContent",
            modifier = Modifier.padding(contentPadding)
        ) { targetState ->
            when (targetState) {
                PlanEditorUiState.Loading -> LoadingContent()
                PlanEditorUiState.Error -> Unit
                is PlanEditorUiState.Loaded -> LoadedContent(
                    plan = targetState.plan,
                    topAppBarScrollBehavior = topAppBarScrollBehavior,
                    onAddDay = onAddDay,
                    onEditDay = onEditDay,
                    onDeleteDay = onDeleteDay,
                    onAddExercise = onAddExercise,
                    onEditExercise = onEditExercise,
                    onDeleteExercise = onDeleteExercise,
                    onReorderExercises = onReorderExercises
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopBar(
    state: PlanEditorUiState.Loaded?,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    onBack: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    LargeTopAppBar(
        title = {
            Text(state?.plan?.name.orEmpty())
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
        },
        actions = {
            var isMenuExpanded by rememberSaveable { mutableStateOf(false) }
            Box {
                IconButton(onClick = { isMenuExpanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = null)
                }
                EditDeleteMenu(
                    isMenuExpanded = isMenuExpanded,
                    onDismiss = { isMenuExpanded = false },
                    onEdit = onEdit,
                    onDelete = onDelete
                )
            }
        },
        scrollBehavior = topAppBarScrollBehavior
    )
}

@Composable
private fun LoadingContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun LoadedContent(
    plan: TrainingPlan,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    onAddDay: () -> Unit,
    onEditDay: (dayId: String) -> Unit,
    onDeleteDay: (dayId: String) -> Unit,
    onAddExercise: (dayId: String) -> Unit,
    onEditExercise: (dayId: String, exerciseId: String) -> Unit,
    onDeleteExercise: (dayId: String, exerciseId: String) -> Unit,
    onReorderExercises: (dayId: String, exercisesIds: List<String>) -> Unit
) {
    var mutablePlan by remember(plan) { mutableStateOf(plan) }
    val lazyListState = rememberLazyListState()
    val reorderableLazyListState = rememberReorderableLazyListState(
        lazyListState,
        scrollThresholdPadding = WindowInsets.systemBars.asPaddingValues()
    ) { from, to ->
        mutablePlan = mutablePlan.changeExercisesOrder(
            fromId = from.key as String,
            toId = to.key as String
        )
    }

    LazyColumn(
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
    ) {
        mutablePlan.days.forEach { day ->
            stickyHeader {
                DayHeader(
                    name = day.name,
                    onEdit = { onEditDay(day.id) },
                    onDelete = { onDeleteDay(day.id) }
                )
            }
            items(day.exercises, key = { it.id }) { exercise ->
                val currentDay by rememberUpdatedState(day)
                ExerciseItem(
                    reorderableLazyListState = reorderableLazyListState,
                    id = exercise.id,
                    name = exercise.name,
                    sets = exercise.sets,
                    reps = exercise.reps,
                    onEdit = {
                        onEditExercise(day.id, exercise.id)
                    },
                    onDelete = {
                        onDeleteExercise(day.id, exercise.id)
                    },
                    onDragStopped = {
                        val exercisesIds = currentDay.exercises.map { it.id }
                        onReorderExercises(day.id, exercisesIds)
                    }
                )
            }
            item {
                AddExerciseButton(onClick = { onAddExercise(day.id) })
            }
        }
        item {
            AddDayButton(onClick = onAddDay)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LazyItemScope.DayHeader(name: String, onEdit: () -> Unit, onDelete: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = name)
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        actions = {
            Box {
                var isMenuExpanded by rememberSaveable { mutableStateOf(false) }
                IconButton(onClick = { isMenuExpanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = null)
                }
                EditDeleteMenu(
                    isMenuExpanded = isMenuExpanded,
                    onDismiss = { isMenuExpanded = false },
                    onEdit = onEdit,
                    onDelete = onDelete
                )
            }
        },
        windowInsets = WindowInsets(top = 0),
        modifier = Modifier.animateItem()
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun LazyItemScope.ExerciseItem(
    reorderableLazyListState: ReorderableLazyListState,
    id: String,
    name: String,
    sets: Sets,
    reps: Reps,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onDragStopped: () -> Unit
) {
    ReorderableItem(state = reorderableLazyListState, key = id) {
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
                        text = name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Sets: $sets",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Reps: $reps",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                var isMenuExpanded by rememberSaveable { mutableStateOf(false) }
                Box {
                    IconButton(onClick = { isMenuExpanded = true }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                    }
                    EditDeleteMenu(
                        isMenuExpanded = isMenuExpanded,
                        onDismiss = { isMenuExpanded = false },
                        onEdit = onEdit,
                        onDelete = onDelete
                    )
                }
            }
        }
    }
}

@Composable
private fun EditDeleteMenu(
    isMenuExpanded: Boolean,
    onDismiss: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    DropdownMenu(
        expanded = isMenuExpanded,
        onDismissRequest = onDismiss
    ) {
        DropdownMenuItem(
            text = {
                Text("Edit")
            },
            leadingIcon = {
                Icon(Icons.Default.Edit, contentDescription = null)
            },
            onClick = {
                onDismiss()
                onEdit()
            }
        )
        DropdownMenuItem(
            text = {
                Text("Delete")
            },
            leadingIcon = {
                Icon(Icons.Default.Delete, contentDescription = null)
            },
            onClick = {
                onDismiss()
                onDelete()
            }
        )
    }
}

@Composable
private fun LazyItemScope.AddDayButton(onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .animateItem()
            .padding(horizontal = 16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.EditCalendar,
            contentDescription = null,
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
        Text(text = "Add day")
    }
}

@Composable
private fun LazyItemScope.AddExerciseButton(onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .animateItem()
            .padding(horizontal = 16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.FitnessCenter,
            contentDescription = null,
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
        Text(text = "Add exercise")
    }
}

@Preview
@Composable
private fun PlanEditorScreen_LoadedPreview() {
    MaterialTheme {
        PlanEditorScreen(
            state = PlanEditorUiState.Loaded(plan = sampleTrainingPlans.first()),
            onBack = {},
            onEditName = {},
            onDeletePlan = {},
            onAddDay = {},
            onEditDay = {},
            onDeleteDay = {},
            onAddExercise = {},
            onEditExercise = { _, _ -> },
            onDeleteExercise = { _, _ -> },
            onReorderExercises = { _, _ -> }
        )
    }
}

@Preview
@Composable
private fun PlanEditorScreen_EmptyPreview() {
    MaterialTheme {
        PlanEditorScreen(
            state = PlanEditorUiState.Loaded(plan = sampleTrainingPlans.last()),
            onBack = {},
            onEditName = {},
            onDeletePlan = {},
            onAddDay = {},
            onEditDay = {},
            onDeleteDay = {},
            onAddExercise = {},
            onEditExercise = { _, _ -> },
            onDeleteExercise = { _, _ -> },
            onReorderExercises = { _, _ -> }
        )
    }
}

@Preview
@Composable
private fun PlanEditorScreen_LoadingPreview() {
    MaterialTheme {
        PlanEditorScreen(
            state = PlanEditorUiState.Loading,
            onBack = {},
            onEditName = {},
            onDeletePlan = {},
            onAddDay = {},
            onEditDay = {},
            onDeleteDay = {},
            onAddExercise = {},
            onEditExercise = { _, _ -> },
            onDeleteExercise = { _, _ -> },
            onReorderExercises = { _, _ -> }
        )
    }
}
