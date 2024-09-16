package com.maruchin.gymster.android.trainings

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.maruchin.gymster.android.ui.AppTheme
import com.maruchin.gymster.android.ui.LoadingContent
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets
import com.maruchin.gymster.data.trainings.model.Exercise
import com.maruchin.gymster.data.trainings.model.SetResult
import com.maruchin.gymster.data.trainings.model.sampleTrainingBlocks
import com.maruchin.gymster.feature.trainings.trainingeditor.TrainingEditorUiState
import com.maruchin.gymster.feature.trainings.trainingeditor.TrainingEditorViewModel
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@Composable
internal fun TrainingEditorScreen(
    trainingBlockId: String,
    trainingId: String,
    exerciseId: String,
    onBack: () -> Unit,
    viewModel: TrainingEditorViewModel = viewModel {
        TrainingEditorViewModel(trainingBlockId, trainingId, exerciseId)
    }
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    TrainingEditorScreen(
        state = state,
        onBack = onBack,
        onUpdateWeight = viewModel::updateWeight,
        onUpdateReps = viewModel::updateReps
    )
}

@Composable
private fun TrainingEditorScreen(
    state: TrainingEditorUiState,
    onBack: () -> Unit,
    onUpdateWeight: (setResultId: String, weight: Double?) -> Unit,
    onUpdateReps: (setResultId: String, reps: Int?) -> Unit
) {
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
                    onUpdateWeight = onUpdateWeight,
                    onUpdateReps = onUpdateReps
                )
            }
        }
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
    onUpdateWeight: (setResultId: String, weight: Double?) -> Unit,
    onUpdateReps: (setResultId: String, reps: Int?) -> Unit
) {
    val firstPage = 0
    val lastPage = state.training.exercises.lastIndex
    val pagerState = rememberPagerState(
        initialPage = state.training.getExerciseIndex(state.initialExerciseId),
        pageCount = { state.training.exercises.size }
    )
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.imePadding()) {
        ExercisesTabRow(pagerState = pagerState, state = state, scope = scope)
        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
            ExercisePage(
                exercise = state.training.exercises[page],
                onUpdateWeight = onUpdateWeight,
                onUpdateReps = onUpdateReps
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
private fun ExercisePage(
    exercise: Exercise,
    onUpdateWeight: (setResultId: String, weight: Double?) -> Unit,
    onUpdateReps: (setResultId: String, reps: Int?) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        ExerciseItem(
            name = exercise.name,
            sets = exercise.sets,
            reps = exercise.reps
        )
        exercise.results.forEach { setResult ->
            SetResultItem(
                setResult = setResult,
                isLast = setResult == exercise.results.last(),
                onUpdateWeight = { onUpdateWeight(setResult.id, it) },
                onUpdateReps = { onUpdateReps(setResult.id, it) }
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

@OptIn(FlowPreview::class)
@Composable
private fun SetResultItem(
    setResult: SetResult,
    isLast: Boolean,
    onUpdateWeight: (Double?) -> Unit,
    onUpdateReps: (Int?) -> Unit
) {
    val currentOnUpdateWeight by rememberUpdatedState(onUpdateWeight)
    val currentOnUpdateReps by rememberUpdatedState(onUpdateReps)
    var weightInput by rememberSaveable(setResult) {
        mutableStateOf(setResult.weight?.toString().orEmpty())
    }
    var repsInput by rememberSaveable(setResult) {
        mutableStateOf(setResult.reps?.toString().orEmpty())
    }

    LaunchedEffect(weightInput) {
        snapshotFlow { weightInput }.debounce(1.seconds).collectLatest {
            currentOnUpdateWeight(weightInput.toDoubleOrNull())
        }
    }

    LaunchedEffect(repsInput) {
        snapshotFlow { repsInput }.debounce(1.seconds).collectLatest {
            currentOnUpdateReps(repsInput.toIntOrNull())
        }
    }

    Column {
        Text(
            text = when (setResult.type) {
                SetResult.Type.REGULAR -> "Regular set"
                SetResult.Type.DROP -> "Drop set"
            },
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.height(IntrinsicSize.Max)
        ) {
            OutlinedTextField(
                value = weightInput,
                onValueChange = { weightInput = it },
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text(text = "--")
                },
                singleLine = true,
                suffix = {
                    Text(text = "kg")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next
                )
            )
            Icon(imageVector = Icons.Default.Close, contentDescription = null)
            OutlinedTextField(
                value = repsInput,
                onValueChange = { repsInput = it },
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text(text = "--")
                },
                singleLine = true,
                suffix = {
                    Text(text = "reps")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = if (isLast) ImeAction.Done else ImeAction.Next
                )
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
                training = sampleTrainingBlocks.first().weeks.first().trainings.first(),
                initialExerciseId = sampleTrainingBlocks.first().weeks.first().trainings.first()
                    .exercises.first().id
            ),
            onBack = {},
            onUpdateWeight = { _, _ -> },
            onUpdateReps = { _, _ -> }
        )
    }
}
