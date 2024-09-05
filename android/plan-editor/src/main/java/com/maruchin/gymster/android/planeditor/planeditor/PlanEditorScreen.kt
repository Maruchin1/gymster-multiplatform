package com.maruchin.gymster.android.planeditor.planeditor

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.maruchin.gymster.android.planeditor.planform.PlanFormModal
import com.maruchin.gymster.android.ui.AppTheme
import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.plans.model.PlannedWeek
import com.maruchin.gymster.data.plans.model.samplePlans
import com.maruchin.gymster.feature.planeditor.planeditor.PlanEditorUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import sh.calvin.reorderable.rememberReorderableLazyListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PlanEditorScreen(
    state: PlanEditorUiState,
    onBack: () -> Unit,
    onChangePlanName: (newName: String) -> Unit,
    onDeletePlan: () -> Unit,
    onAddTraining: () -> Unit,
    onEditTraining: (trainingId: String) -> Unit,
    onDeleteTraining: (trainingId: String) -> Unit,
    onAddExercise: (trainingId: String) -> Unit,
    onEditExercise: (trainingId: String, exerciseId: String) -> Unit,
    onDeleteExercise: (trainingId: String, exerciseId: String) -> Unit,
    onReorderExercises: (trainingId: String, exercisesIds: List<String>) -> Unit
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val loadedState = state as? PlanEditorUiState.Loaded
    var isEditingName by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBar(
                plan = loadedState?.plan,
                topAppBarScrollBehavior = topAppBarScrollBehavior,
                onBack = onBack,
                onChangePlanName = { isEditingName = true },
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
                    onAddTraining = onAddTraining,
                    onEditTraining = onEditTraining,
                    onDeleteTraining = onDeleteTraining,
                    onAddExercise = onAddExercise,
                    onEditExercise = onEditExercise,
                    onDeleteExercise = onDeleteExercise,
                    onReorderExercises = onReorderExercises
                )
            }
        }
    }

    if (isEditingName) {
        PlanFormModal(
            plan = loadedState?.plan,
            onDismiss = { isEditingName = false },
            onSave = onChangePlanName
        )
    }
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
    onEditTraining: (trainingId: String) -> Unit,
    onDeleteTraining: (trainingId: String) -> Unit,
    onAddExercise: (trainingId: String) -> Unit,
    onEditExercise: (trainingId: String, exerciseId: String) -> Unit,
    onDeleteExercise: (trainingId: String, exerciseId: String) -> Unit,
    onReorderExercises: (trainingId: String, exercisesIds: List<String>) -> Unit
) {
    val pagerState = rememberPagerState { plan.weeks.size }
    val scope = rememberCoroutineScope()

    Column {
        WeeksTabs(
            weeks = plan.weeks,
            pagerState = pagerState,
            scope = scope
        )
        HorizontalPager(state = pagerState) { page ->
            WeekPage(
                week = plan.weeks[page],
                topAppBarScrollBehavior = topAppBarScrollBehavior,
                onAddTraining = onAddTraining,
                onEditTraining = onEditTraining,
                onDeleteTraining = onDeleteTraining,
                onAddExercise = onAddExercise,
                onEditExercise = onEditExercise,
                onDeleteExercise = onDeleteExercise,
                onReorderExercises = onReorderExercises
            )
        }
    }
}

@Composable
private fun WeeksTabs(weeks: List<PlannedWeek>, pagerState: PagerState, scope: CoroutineScope) {
    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        modifier = Modifier.fillMaxWidth()
    ) {
        weeks.forEachIndexed { index, _ ->
            Tab(
                selected = index == pagerState.currentPage,
                onClick = {
                    scope.launch { pagerState.animateScrollToPage(index) }
                },
                text = {
                    Text(text = "Week ${index + 1}")
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun WeekPage(
    week: PlannedWeek,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    onAddTraining: () -> Unit,
    onEditTraining: (trainingId: String) -> Unit,
    onDeleteTraining: (trainingId: String) -> Unit,
    onAddExercise: (trainingId: String) -> Unit,
    onEditExercise: (trainingId: String, exerciseId: String) -> Unit,
    onDeleteExercise: (trainingId: String, exerciseId: String) -> Unit,
    onReorderExercises: (trainingId: String, exercisesIds: List<String>) -> Unit
) {
    var mutableWeek by remember(week) { mutableStateOf(week) }
    val lazyListState = rememberLazyListState()
    val reorderableLazyListState = rememberReorderableLazyListState(
        lazyListState = lazyListState,
        scrollThresholdPadding = WindowInsets.systemBars.asPaddingValues()
    ) { from, to ->
        mutableWeek = mutableWeek.changeExerciseOrder(
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
        mutableWeek.trainings.forEachIndexed { trainingIndex, training ->
            stickyHeader {
                TrainingHeader(
                    training = training,
                    onEdit = { onEditTraining(training.id) },
                    onDelete = { onDeleteTraining(training.id) }
                )
            }
            items(training.exercises, key = { it.id }) { exercise ->
                val currentTraining by rememberUpdatedState(training)
                PlannedExerciseItem(
                    exercise = exercise,
                    reorderableLazyListState = reorderableLazyListState,
                    onEdit = {
                        onEditExercise(training.id, exercise.id)
                    },
                    onDelete = {
                        onDeleteExercise(training.id, exercise.id)
                    },
                    onDragStopped = {
                        val exercisesIds = currentTraining.exercises.map { it.id }
                        onReorderExercises(training.id, exercisesIds)
                    }
                )
            }
            item {
                AddExerciseButton(onClick = { onAddExercise(training.id) })
            }
            if (trainingIndex != week.trainings.lastIndex) {
                item {
                    HorizontalDivider()
                }
            }
        }
        item {
            AddTrainingButton(onClick = onAddTraining)
        }
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
            onChangePlanName = {},
            onDeletePlan = {},
            onAddTraining = {},
            onEditTraining = {},
            onDeleteTraining = {},
            onAddExercise = {},
            onEditExercise = { _, _ -> },
            onDeleteExercise = { _, _ -> },
            onReorderExercises = { _, _ -> }
        )
    }
}
