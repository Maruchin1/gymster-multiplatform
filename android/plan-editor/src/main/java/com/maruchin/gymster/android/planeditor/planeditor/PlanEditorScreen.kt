package com.maruchin.gymster.android.planeditor.planeditor

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.DragHandle
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.maruchin.gymster.android.planeditor.exerciseform.ExerciseFormModal
import com.maruchin.gymster.android.planeditor.planform.PlanFormModal
import com.maruchin.gymster.android.planeditor.trainingform.TrainingFormModal
import com.maruchin.gymster.android.ui.AppTheme
import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.plans.model.PlannedExercise
import com.maruchin.gymster.data.plans.model.PlannedTraining
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets
import com.maruchin.gymster.data.plans.model.samplePlans
import com.maruchin.gymster.feature.planeditor.planeditor.PlanEditorUiState
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.ReorderableLazyListState
import sh.calvin.reorderable.rememberReorderableLazyListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PlanEditorScreen(
    state: PlanEditorUiState,
    onBack: () -> Unit,
    onUpdatePlan: (newName: String) -> Unit,
    onAddTraining: (name: String) -> Unit,
    onUpdateTraining: (trainingId: String, name: String) -> Unit,
    onDeletePlan: () -> Unit,
    onDeleteTraining: (trainingId: String) -> Unit,
    onAddExercise: (trainingId: String, name: String, sets: Sets, reps: Reps) -> Unit,
    onUpdateExercise: (exerciseId: String, name: String, sets: Sets, reps: Reps) -> Unit,
    onDeleteExercise: (exerciseId: String) -> Unit,
    onReorderExercises: (trainingId: String, exercisesIds: List<String>) -> Unit
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val loadedState = state as? PlanEditorUiState.Loaded

    var isEditingPlan by remember { mutableStateOf(false) }
    var isAddingTraining by remember { mutableStateOf(false) }
    var editedTraining by remember { mutableStateOf<PlannedTraining?>(null) }

    Scaffold(
        topBar = {
            TopBar(
                plan = loadedState?.plan,
                scrollBehavior = topAppBarScrollBehavior,
                onBack = onBack,
                onEditPlan = { isEditingPlan = true },
                onDeletePlan = onDeletePlan
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
                is PlanEditorUiState.Loaded -> LoadedContent(
                    plan = targetState.plan,
                    topAppBarScrollBehavior = topAppBarScrollBehavior,
                    onAddTraining = { isAddingTraining = true },
                    onUpdateTraining = onUpdateTraining,
                    onDeleteTraining = onDeleteTraining,
                    onAddExercise = onAddExercise,
                    onUpdateExercise = onUpdateExercise,
                    onDeleteExercise = onDeleteExercise,
                    onReorderExercises = onReorderExercises
                )
            }
        }
    }

    if (isEditingPlan) {
        PlanFormModal(
            plan = loadedState?.plan,
            onDismiss = { isEditingPlan = false },
            onSave = onUpdatePlan
        )
    }
    if (isAddingTraining) {
        TrainingFormModal(
            training = null,
            onDismiss = { isAddingTraining = false },
            onSave = onAddTraining
        )
    }
    if (editedTraining != null) {
        TrainingFormModal(
            training = editedTraining,
            onDismiss = { editedTraining = null },
            onSave = { onUpdateTraining(editedTraining!!.id, it) }
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopBar(
    plan: Plan?,
    scrollBehavior: TopAppBarScrollBehavior,
    onBack: () -> Unit,
    onEditPlan: () -> Unit,
    onDeletePlan: () -> Unit
) {
    LargeTopAppBar(
        title = {
            Text(plan?.name.orEmpty())
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = onEditPlan) {
                Icon(imageVector = Icons.Outlined.Edit, contentDescription = null)
            }
            IconButton(onClick = onDeletePlan) {
                Icon(imageVector = Icons.Outlined.Delete, contentDescription = null)
            }
        },
        scrollBehavior = scrollBehavior
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoadedContent(
    plan: Plan,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    onAddTraining: () -> Unit,
    onUpdateTraining: (trainingId: String, name: String) -> Unit,
    onDeleteTraining: (trainingId: String) -> Unit,
    onAddExercise: (trainingId: String, name: String, sets: Sets, reps: Reps) -> Unit,
    onUpdateExercise: (exerciseId: String, name: String, sets: Sets, reps: Reps) -> Unit,
    onDeleteExercise: (exerciseId: String) -> Unit,
    onReorderExercises: (trainingId: String, exercisesIds: List<String>) -> Unit
) {
    var mutablePlan by remember(plan) { mutableStateOf(plan) }

    val lazyListState = rememberLazyListState()
    val reorderableLazyListState = rememberReorderableLazyListState(
        lazyListState = lazyListState,
        scrollThresholdPadding = WindowInsets.systemBars.asPaddingValues()
    ) { from, to ->
        mutablePlan = mutablePlan.changeExerciseOrder(
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
        mutablePlan.trainings.forEach { training ->
            trainingSection(
                training = training,
                onUpdateTraining = { name ->
                    onUpdateTraining(training.id, name)
                },
                onDeleteTraining = { onDeleteTraining(training.id) },
                reorderableLazyListState = reorderableLazyListState,
                onUpdateExercise = onUpdateExercise,
                onDeleteExercise = onDeleteExercise,
                onReorderExercises = onReorderExercises,
                onAddExercise = onAddExercise
            )
        }
        item {
            AddTrainingButton(onClick = onAddTraining)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun LazyListScope.trainingSection(
    training: PlannedTraining,
    reorderableLazyListState: ReorderableLazyListState,
    onUpdateTraining: (name: String) -> Unit,
    onDeleteTraining: () -> Unit,
    onAddExercise: (trainingId: String, name: String, sets: Sets, reps: Reps) -> Unit,
    onUpdateExercise: (exerciseId: String, name: String, sets: Sets, reps: Reps) -> Unit,
    onDeleteExercise: (exerciseId: String) -> Unit,
    onReorderExercises: (trainingId: String, exercisesIds: List<String>) -> Unit
) {
    stickyHeader {
        var isEditingTraining by rememberSaveable { mutableStateOf(false) }

        TrainingHeader(
            training = training,
            onEditTraining = { isEditingTraining = true },
            onDeleteTraining = onDeleteTraining
        )

        if (isEditingTraining) {
            TrainingFormModal(
                training = training,
                onDismiss = { isEditingTraining = false },
                onSave = onUpdateTraining
            )
        }
    }
    items(training.exercises, key = { it.id }) { exercise ->
        ExerciseItem(
            exercise = exercise,
            reorderableLazyListState = reorderableLazyListState,
            onUpdateExercise = { name, sets, reps ->
                onUpdateExercise(exercise.id, name, sets, reps)
            },
            onDeleteExercise = { onDeleteExercise(exercise.id) },
            onDragStop = {
                val exercisesIds = training.exercises.map { it.id }
                onReorderExercises(training.id, exercisesIds)
            }
        )
    }
    item {
        var isAddingExercise by rememberSaveable { mutableStateOf(false) }

        AddExerciseButton(onClick = { isAddingExercise = true })

        if (isAddingExercise) {
            ExerciseFormModal(
                exercise = null,
                onSave = { name, sets, reps ->
                    onAddExercise(training.id, name, sets, reps)
                    isAddingExercise = false
                },
                onDismiss = { isAddingExercise = false }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LazyItemScope.TrainingHeader(
    training: PlannedTraining,
    onEditTraining: () -> Unit,
    onDeleteTraining: () -> Unit
) {
    Column {
        HorizontalDivider()
        TopAppBar(
            title = {
                Text(text = training.name)
            },
            actions = {
                IconButton(onClick = onEditTraining) {
                    Icon(imageVector = Icons.Outlined.Edit, contentDescription = null)
                }
                IconButton(onClick = onDeleteTraining) {
                    Icon(imageVector = Icons.Outlined.Delete, contentDescription = null)
                }
            },
            windowInsets = WindowInsets(top = 0),
            modifier = Modifier.animateItem()
        )
        HorizontalDivider()
    }
}

@Composable
private fun LazyItemScope.AddTrainingButton(onClick: () -> Unit) {
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
        Text(text = "Add training")
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun LazyItemScope.ExerciseItem(
    exercise: PlannedExercise,
    reorderableLazyListState: ReorderableLazyListState,
    onUpdateExercise: (name: String, sets: Sets, reps: Reps) -> Unit,
    onDeleteExercise: () -> Unit,
    onDragStop: () -> Unit
) {
    var isEditingExercise by rememberSaveable { mutableStateOf(false) }

    ReorderableItem(state = reorderableLazyListState, key = exercise.id) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .animateItem()
                .padding(horizontal = 16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 12.dp, start = 12.dp, end = 8.dp, bottom = 2.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = exercise.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Sets: ${exercise.sets}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Reps: ${exercise.reps}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        AssistChip(
                            onClick = { isEditingExercise = true },
                            label = {
                                Text(text = "Edit")
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Edit,
                                    contentDescription = null,
                                    modifier = Modifier.size(AssistChipDefaults.IconSize)
                                )
                            }
                        )
                        AssistChip(
                            onClick = onDeleteExercise,
                            label = {
                                Text(text = "Delete")
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Delete,
                                    contentDescription = null,
                                    modifier = Modifier.size(AssistChipDefaults.IconSize)

                                )
                            }
                        )
                    }
                }
                IconButton(
                    onClick = {},
                    modifier = Modifier.draggableHandle(onDragStopped = onDragStop)
                ) {
                    Icon(imageVector = Icons.Default.DragHandle, contentDescription = null)
                }
            }
        }
    }

    if (isEditingExercise) {
        ExerciseFormModal(
            exercise = exercise,
            onSave = onUpdateExercise,
            onDismiss = { isEditingExercise = false }
        )
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

@PreviewLightDark
@Composable
private fun PlanEditorScreen_LoadedPreview() {
    AppTheme {
        PlanEditorScreen(
            state = PlanEditorUiState.Loaded(plan = samplePlans.first()),
            onBack = {},
            onUpdatePlan = {},
            onAddTraining = { },
            onUpdateTraining = { _, _ -> },
            onDeletePlan = {},
            onDeleteTraining = {},
            onAddExercise = { _, _, _, _ -> },
            onUpdateExercise = { _, _, _, _ -> },
            onDeleteExercise = { _ -> },
            onReorderExercises = { _, _ -> }
        )
    }
}
