package com.maruchin.gymster.android.trainings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.maruchin.gymster.android.ui.AppTheme
import com.maruchin.gymster.core.utils.toggle
import com.maruchin.gymster.data.trainings.model.Exercise
import com.maruchin.gymster.data.trainings.model.Training
import com.maruchin.gymster.data.trainings.model.TrainingBlock
import com.maruchin.gymster.data.trainings.model.TrainingWeek
import com.maruchin.gymster.data.trainings.model.sampleTrainingBlocks
import com.maruchin.gymster.feature.trainings.trainingblockeditor.TrainingBlockEditorUiState
import com.maruchin.gymster.feature.trainings.trainingblockeditor.TrainingBlockEditorViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun TrainingBlockEditorScreen(
    trainingBlockId: String,
    onBack: () -> Unit,
    onOpenTraining: (trainingId: String, exerciseId: String) -> Unit,
    viewModel: TrainingBlockEditorViewModel = viewModel {
        TrainingBlockEditorViewModel(trainingBlockId)
    }
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    TrainingBlockEditorScreen(
        state = state,
        onBack = onBack,
        onOpenTraining = onOpenTraining
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TrainingBlockEditorScreen(
    state: TrainingBlockEditorUiState,
    onBack: () -> Unit,
    onOpenTraining: (trainingId: String, exerciseId: String) -> Unit
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            TopBar(
                trainingBlock = state.trainingBlock,
                topAppBarScrollBehavior = topAppBarScrollBehavior,
                onBack = onBack
            )
        }
    ) { contentPadding ->
        val loadedTrainingBlock = state.trainingBlock ?: return@Scaffold
        val weeks = loadedTrainingBlock.weeks
        val pagerState = rememberPagerState(
            initialPage = loadedTrainingBlock.currentWeekIndex,
            pageCount = { weeks.size }
        )
        val scope = rememberCoroutineScope()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            WeeksTabs(weeks = weeks, pagerState = pagerState, scope = scope)
            HorizontalPager(state = pagerState) { page ->
                WeekPage(
                    week = weeks[page],
                    topAppBarScrollBehavior = topAppBarScrollBehavior,
                    onOpenTraining = onOpenTraining
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopBar(
    trainingBlock: TrainingBlock?,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    onBack: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = trainingBlock?.planName.orEmpty())
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = null
                )
            }
        },
        scrollBehavior = topAppBarScrollBehavior
    )
}

@Composable
private fun WeeksTabs(weeks: List<TrainingWeek>, pagerState: PagerState, scope: CoroutineScope) {
    ScrollableTabRow(selectedTabIndex = pagerState.currentPage) {
        weeks.forEachIndexed { index, trainingWeek ->
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

@Composable
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
private fun WeekPage(
    week: TrainingWeek,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    onOpenTraining: (trainingId: String, exerciseId: String) -> Unit
) {
    var expandedTrainingIds by rememberSaveable(week) {
        mutableStateOf(week.notCompleteTrainingsIds)
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
        contentPadding = PaddingValues(bottom = 12.dp)
    ) {
        week.trainings.forEachIndexed { index, training ->
            stickyHeader(key = "training" + training.id) {
                TrainingHeader(
                    training = training,
                    isExpanded = training.id in expandedTrainingIds,
                    onExpandedToggle = {
                        expandedTrainingIds = expandedTrainingIds.toggle(training.id)
                    }
                )
            }
            if (training.id in expandedTrainingIds) {
                items(training.exercises, key = { "exercise" + it.id }) { exercise ->
                    ExerciseItem(
                        exercise = exercise,
                        onClick = { onOpenTraining(training.id, exercise.id) }
                    )
                }
                if (index != week.trainings.lastIndex) {
                    item {
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}

@Composable
private fun LazyItemScope.TrainingHeader(
    training: Training,
    isExpanded: Boolean,
    onExpandedToggle: () -> Unit
) {
    val arrowRotation by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        label = "TrainingHeaderArrowRotation"
    )

    Surface(modifier = Modifier.animateItem(), onClick = onExpandedToggle) {
        Column {
            Row(
                modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = training.name,
                    style = MaterialTheme.typography.headlineMedium
                )
                AnimatedVisibility(visible = training.isComplete) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        modifier = Modifier.padding(horizontal = 12.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.ArrowDropUp,
                    contentDescription = null,
                    modifier = Modifier.rotate(arrowRotation)
                )
            }
            HorizontalDivider()
        }
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun LazyItemScope.ExerciseItem(exercise: Exercise, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .animateItem()
            .clickable { onClick() }
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = exercise.name,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.weight(1f)
                    )
                    if (exercise.isComplete) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
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
        }
        Spacer(modifier = Modifier.height(16.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            exercise.results.forEach { setResult ->
                AssistChip(
                    onClick = { },
                    label = {
                        val formattedWeight = setResult.weight?.let { "$it kg" } ?: "--"
                        val formattedReps = setResult.reps?.let { "$it" } ?: "--"
                        Text(text = "$formattedWeight x $formattedReps")
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
    }
}

@PreviewLightDark
@Composable
private fun TrainingBlockEditorScreen_LoadedPreview() {
    AppTheme {
        TrainingBlockEditorScreen(
            state = TrainingBlockEditorUiState(trainingBlock = sampleTrainingBlocks.first()),
            onBack = { },
            onOpenTraining = { _, _ -> }
        )
    }
}
