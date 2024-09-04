package com.maruchin.gymster.android.trainingeditor.editor

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.maruchin.gymster.android.trainingeditor.setprogressform.SetProgressFormModal
import com.maruchin.gymster.android.ui.AppTheme
import com.maruchin.gymster.android.ui.LoadingContent
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets
import com.maruchin.gymster.data.trainings.model.Exercise
import com.maruchin.gymster.data.trainings.model.Progress
import com.maruchin.gymster.data.trainings.model.SetProgress
import com.maruchin.gymster.data.trainings.model.sampleTrainingBlocks
import com.maruchin.gymster.feature.trainingeditor.editor.TrainingEditorUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun TrainingEditorScreen(
    state: TrainingEditorUiState,
    onBack: () -> Unit,
    onUpdateSetProgress: (setProgressId: String, progress: Progress) -> Unit
) {
    var editedSetProgress by remember { mutableStateOf<SetProgress?>(null) }

    Scaffold(
        topBar = {
            TopBar(loadedState = state as? TrainingEditorUiState.Loaded, onBack = onBack)
        }
    ) { contentPadding ->
        AnimatedContent(
            targetState = state,
            contentKey = { it::class },
            label = "TrainingEditorContent",
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            when (it) {
                TrainingEditorUiState.Loading -> LoadingContent()
                is TrainingEditorUiState.Loaded -> LoadedContent(
                    state = it,
                    onEditSetProgress = { setProgressId ->
                        editedSetProgress = setProgressId
                    }
                )
            }
        }
    }

    editedSetProgress?.let { setProgress ->
        SetProgressFormModal(
            setProgress = setProgress,
            onDismiss = { editedSetProgress = null },
            onSave = { progress ->
                onUpdateSetProgress(setProgress.id, progress)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(loadedState: TrainingEditorUiState.Loaded?, onBack: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = loadedState?.training?.name.orEmpty())
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
private fun LoadedContent(
    state: TrainingEditorUiState.Loaded,
    onEditSetProgress: (SetProgress) -> Unit
) {
    val firstPage = 0
    val lastPage = state.training.exercises.lastIndex
    val pagerState = rememberPagerState(
        initialPage = state.training.firstNotCompleteExerciseIndex,
        pageCount = { state.training.exercises.size }
    )
    val scope = rememberCoroutineScope()

    Column {
        ExercisesTabRow(pagerState = pagerState, state = state, scope = scope)
        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
            ExercisePage(
                exercise = state.training.exercises[page],
                onEditSetProgress = onEditSetProgress
            )
        }
        ExerciseActionsRow(
            hasPreviousExercise = pagerState.currentPage > firstPage,
            hasNextExercise = pagerState.currentPage < lastPage,
            onOpenPreviousExercise = {
                val previousPage = pagerState.currentPage - 1
                scope.launch { pagerState.animateScrollToPage(previousPage) }
            },
            onOpenNextExercise = {
                val nextPage = pagerState.currentPage + 1
                scope.launch { pagerState.animateScrollToPage(nextPage) }
            }
        )
    }
}

@Composable
private fun ExercisesTabRow(
    pagerState: PagerState,
    state: TrainingEditorUiState.Loaded,
    scope: CoroutineScope
) {
    TabRow(selectedTabIndex = pagerState.currentPage) {
        state.training.exercises.forEachIndexed { index, exercise ->
            Tab(
                selected = index == pagerState.currentPage,
                onClick = {
                    scope.launch { pagerState.animateScrollToPage(index) }
                },
                text = { Text(text = "${index + 1}") },
                icon = {
                    if (exercise.isComplete) {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null)
                    } else {
                        Icon(imageVector = Icons.Outlined.Circle, contentDescription = null)
                    }
                }
            )
        }
    }
}

@Composable
private fun ExercisePage(exercise: Exercise, onEditSetProgress: (SetProgress) -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        ExerciseItem(
            name = exercise.name,
            sets = exercise.sets,
            reps = exercise.reps
        )
        exercise.setProgress.forEach { setProgress ->
            SetProgressItem(
                type = setProgress.type,
                progress = setProgress.progress,
                onClick = { onEditSetProgress(setProgress) }
            )
        }
    }
}

@Composable
private fun ExerciseItem(name: String, sets: Sets, reps: Reps) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(12.dp)
        ) {
            Text(text = name, style = MaterialTheme.typography.titleLarge)
            Text(
                text = "Sets: $sets",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Reps: $reps",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun SetProgressItem(type: SetProgress.Type, progress: Progress?, onClick: () -> Unit) {
    OutlinedCard(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = when (type) {
                    SetProgress.Type.REGULAR -> "Regular set"
                    SetProgress.Type.DROP -> "Drop set"
                },
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = progress?.toString() ?: "----",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun ExerciseActionsRow(
    hasPreviousExercise: Boolean,
    hasNextExercise: Boolean,
    onOpenPreviousExercise: () -> Unit,
    onOpenNextExercise: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedButton(
            onClick = onOpenPreviousExercise,
            enabled = hasPreviousExercise,
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowLeft,
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
            Text(text = "Previous")
        }
        Button(
            onClick = onOpenNextExercise,
            enabled = hasNextExercise,
            modifier = Modifier.weight(1f)
        ) {
            Text(text = "Next")
            Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowRight,
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun TrainingEditorScreen_LoadedPreview() {
    AppTheme {
        TrainingEditorScreen(
            state = TrainingEditorUiState.Loaded(
                sampleTrainingBlocks.first().weeks.first().trainings.first()
            ),
            onBack = {},
            onUpdateSetProgress = { _, _ -> }
        )
    }
}
