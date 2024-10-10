package com.maruchin.gymster.android.trainings2.trainingeditor

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.maruchin.gymster.android.ui.AppTheme
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets
import com.maruchin.gymster.data.trainings2.model.Evaluation
import com.maruchin.gymster.data.trainings2.model.Exercise
import com.maruchin.gymster.data.trainings2.model.SetResult
import com.maruchin.gymster.data.trainings2.model.Training
import com.maruchin.gymster.data.trainings2.model.sampleTrainings
import com.maruchin.gymster.shared.feature.trainings2.trainingeditor.TrainingEditorUiState
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

// TODO Option to delete training

// TODO Option to change training date

// TODO Action to finish training when all exercises are complete

@Composable
internal fun TrainingEditorScreen(
    state: TrainingEditorUiState,
    onBack: () -> Unit,
    onUpdateWeight: (setResultId: String, weight: Double?) -> Unit,
    onUpdateReps: (setResultId: String, reps: Int?) -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(training = state.training, onBack = onBack)
        }
    ) { contentPadding ->
        val loadedTraining = state.training ?: return@Scaffold
        val firstPage = 0
        val lastPage = loadedTraining.exercises.lastIndex
        val pagerState = rememberPagerState(
            initialPage = 0,
            pageCount = { loadedTraining.exercises.size }
        )
        val scope = rememberCoroutineScope()

        Column(
            modifier = Modifier
                .imePadding()
                .padding(contentPadding)
        ) {
            ExercisesTabRow(pagerState = pagerState, training = loadedTraining, scope = scope)
            HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { exerciseIndex ->
                val exercise = loadedTraining.exercises[exerciseIndex]
                ExercisePage(
                    exercise = exercise,
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(training: Training?, onBack: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = training?.name.orEmpty())
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
private fun ExercisesTabRow(pagerState: PagerState, training: Training, scope: CoroutineScope) {
    TabRow(selectedTabIndex = pagerState.currentPage) {
        training.exercises.forEachIndexed { index, exercise ->
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
            reps = exercise.reps,
            evaluation = exercise.evaluation
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
private fun ExerciseItem(name: String, sets: Sets, reps: Reps, evaluation: Evaluation) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column {
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
            AnimatedContent(targetState = evaluation) { targetState ->
                if (targetState != Evaluation.NONE) {
                    EvaluationBanner(
                        text = when (targetState) {
                            Evaluation.LESS -> "👎 Take less"
                            Evaluation.GOOD -> "👌 Good weight"
                            Evaluation.MORE -> "💪 Take more"
                            else -> ""
                        },
                        color = when (targetState) {
                            Evaluation.LESS -> MaterialTheme.colorScheme.errorContainer
                            Evaluation.MORE -> MaterialTheme.colorScheme.primaryContainer
                            else -> Color.Unspecified
                        },
                        showDivider = targetState == Evaluation.GOOD
                    )
                }
            }
        }
    }
}

@Composable
private fun EvaluationBanner(text: String, color: Color, showDivider: Boolean) {
    Column {
        if (showDivider) {
            HorizontalDivider()
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(color)
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
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
                modifier = Modifier
                    .weight(1f)
                    .onFocusChanged {
                        if (!it.hasFocus) {
                            currentOnUpdateWeight(weightInput.toDoubleOrNull())
                        }
                    },
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
                modifier = Modifier
                    .weight(1f)
                    .onFocusChanged {
                        if (!it.hasFocus) {
                            currentOnUpdateReps(repsInput.toIntOrNull())
                        }
                    },
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
            state = TrainingEditorUiState(training = sampleTrainings.first()),
            onBack = {},
            onUpdateWeight = { _, _ -> },
            onUpdateReps = { _, _ -> }
        )
    }
}
