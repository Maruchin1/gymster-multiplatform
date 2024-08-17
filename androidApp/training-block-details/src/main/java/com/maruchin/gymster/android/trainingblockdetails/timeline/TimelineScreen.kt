package com.maruchin.gymster.android.trainingblockdetails.timeline

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.maruchin.gymster.android.ui.AppTheme
import com.maruchin.gymster.android.ui.LoadingContent
import com.maruchin.gymster.data.trainings.model.Exercise
import com.maruchin.gymster.data.trainings.model.Training
import com.maruchin.gymster.data.trainings.model.TrainingWeek
import com.maruchin.gymster.data.trainings.model.sampleTrainingBlocks
import com.maruchin.gymster.feature.trainingblockdetails.timeline.TimelineUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TimelineScreen(
    state: TimelineUiState,
    onBack: () -> Unit,
    onEditProgress: (setProgressId: String) -> Unit
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            TopBar(
                loadedState = state as? TimelineUiState.Loaded,
                topAppBarScrollBehavior = topAppBarScrollBehavior,
                onBack = onBack
            )
        }
    ) { contentPadding ->
        AnimatedContent(
            targetState = state,
            contentKey = { it::class },
            label = "TimelineScreenAnimatedContent",
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            when (it) {
                TimelineUiState.Loading -> LoadingContent()
                is TimelineUiState.Loaded -> LoadedContent(
                    state = it,
                    topAppBarScrollBehavior = topAppBarScrollBehavior,
                    onEditProgress = onEditProgress
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopBar(
    loadedState: TimelineUiState.Loaded?,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    onBack: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = loadedState?.trainingBlock?.planName.orEmpty())
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadedContent(
    state: TimelineUiState.Loaded,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    onEditProgress: (setProgressId: String) -> Unit
) {
    val weeks = state.trainingBlock.weeks
    val pagerState = rememberPagerState(
        initialPage = state.trainingBlock.currentWeekIndex,
        pageCount = { weeks.size }
    )
    val scope = rememberCoroutineScope()

    Column {
        WeeksTabs(weeks = weeks, pagerState = pagerState, scope = scope)
        HorizontalPager(state = pagerState) { page ->
            WeekPage(
                week = weeks[page],
                topAppBarScrollBehavior = topAppBarScrollBehavior,
                onEditProgress = onEditProgress
            )
        }
    }
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
    onEditProgress: (setProgressId: String) -> Unit
) {
    val trainingsListState = rememberTrainingsListState(
        expandedTrainings = week.notCompleteTrainings
    )

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
        contentPadding = PaddingValues(bottom = 12.dp)
    ) {
        week.trainings.forEachIndexed { index, training ->
            stickyHeader(key = "training" + training.id) {
                TrainingHeader(
                    training = training,
                    isExpanded = trainingsListState.isExpanded(training),
                    onToggleExpanded = { trainingsListState.toggleExpanded(training) }
                )
            }
            if (trainingsListState.isExpanded(training)) {
                items(training.exercises, key = { "exercise" + it.id }) { exercise ->
                    ExerciseItem(
                        exercise = exercise,
                        onEditProgress = onEditProgress
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
    onToggleExpanded: () -> Unit
) {
    val arrowRotation by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        label = "TrainingHeaderArrowRotation"
    )

    Surface(modifier = Modifier.animateItem()) {
        Column {
            Row(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .padding(start = 12.dp, end = 8.dp),
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
                IconButton(onClick = onToggleExpanded) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropUp,
                        contentDescription = null,
                        modifier = Modifier.rotate(arrowRotation)
                    )
                }
            }
            HorizontalDivider()
        }
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun LazyItemScope.ExerciseItem(
    exercise: Exercise,
    onEditProgress: (setProgressId: String) -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .animateItem(),
        colors = if (exercise.isComplete) {
            CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        } else {
            CardDefaults.elevatedCardColors()
        }
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
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null)
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
            exercise.progress.forEach { progress ->
                AssistChip(
                    onClick = { onEditProgress(progress.id) },
                    label = {
                        Text(text = progress.progress?.toString() ?: "----")
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
    }
}

@PreviewLightDark
@Composable
private fun TimelineScreen_LoadedPreview() {
    AppTheme {
        TimelineScreen(
            state = TimelineUiState.Loaded(trainingBlock = sampleTrainingBlocks.first()),
            onBack = { },
            onEditProgress = { }
        )
    }
}
