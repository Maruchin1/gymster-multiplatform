package com.maruchin.gymster.android.trainings.starttraining

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.maruchin.gymster.android.ui.AppTheme
import com.maruchin.gymster.android.ui.formatFull
import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.plans.model.PlannedTraining
import com.maruchin.gymster.data.plans.model.samplePlans
import com.maruchin.gymster.shared.feature.trainings.starttraining.StartTrainingUiState
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun StartTrainingScreen(
    state: StartTrainingUiState,
    onBack: () -> Unit,
    onEditPlans: () -> Unit,
    onSelectPlan: (Plan) -> Unit,
    onResetPlan: () -> Unit,
    onSelectTraining: (PlannedTraining) -> Unit,
    onResetTraining: () -> Unit,
    onSelectDate: (LocalDate) -> Unit,
    onResetDate: () -> Unit,
    onStart: () -> Unit
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        topBar = {
            TopBar(onBack = onBack, scrollBehavior = topAppBarScrollBehavior)
        }
    ) { contentPadding ->
        LoadedContent(
            state = state,
            modifier = Modifier.padding(contentPadding),
            topAppBarScrollBehavior = topAppBarScrollBehavior,
            onEditPlans = onEditPlans,
            onSelectPlan = onSelectPlan,
            onResetPlan = onResetPlan,
            onSelectTraining = onSelectTraining,
            onResetTraining = onResetTraining,
            onSelectDate = onSelectDate,
            onResetDate = onResetDate,
            onStart = onStart
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopBar(onBack: () -> Unit, scrollBehavior: TopAppBarScrollBehavior) {
    TopAppBar(
        title = {
            Text(text = "Training block")
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = null
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoadedContent(
    state: StartTrainingUiState,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    onEditPlans: () -> Unit,
    onSelectPlan: (Plan) -> Unit,
    onResetPlan: () -> Unit,
    onSelectTraining: (PlannedTraining) -> Unit,
    onResetTraining: () -> Unit,
    onSelectDate: (LocalDate) -> Unit,
    onResetDate: () -> Unit,
    onStart: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        Column(
            modifier = Modifier
                .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
                .verticalScroll(rememberScrollState())
                .weight(1f)
                .padding(16.dp)
                .then(modifier),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SelectorHeader(text = "Date")
            AnimatedContent(
                targetState = state.selectedDate,
                label = "DateSelector"
            ) { selectedDate ->
                if (selectedDate == null) {
                    DateSelector(onSelectDate = onSelectDate)
                } else {
                    SelectedItem(
                        text = selectedDate.formatFull(),
                        onClick = onResetDate
                    )
                }
            }
            SelectorHeader(
                text = "Plan",
                action = {
                    TextButton(onClick = onEditPlans) {
                        Text("Edit plans")
                    }
                }
            )
            AnimatedContent(
                targetState = state.selectedPlan,
                label = "PlanSelector"
            ) { selectedPlan ->
                if (selectedPlan == null) {
                    PlanSelector(plans = state.plans, onSelectPlan = onSelectPlan)
                } else {
                    SelectedItem(text = selectedPlan.name, onClick = onResetPlan)
                }
            }
            AnimatedVisibility(visible = state.selectedPlan != null) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    SelectorHeader(text = "Training")
                    AnimatedContent(
                        targetState = state.selectedTraining,
                        label = "TrainingSelector"
                    ) { selectedTraining ->
                        if (selectedTraining == null) {
                            TrainingSelector(
                                trainings = state.trainings,
                                onSelectTraining = onSelectTraining
                            )
                        } else {
                            SelectedItem(text = selectedTraining.name, onClick = onResetTraining)
                        }
                    }
                }
            }
        }
        StartButton(
            enabled = state.canCreate,
            onClick = onStart,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
private fun SelectorHeader(
    text: String,
    modifier: Modifier = Modifier,
    action: @Composable (() -> Unit)? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(top = 16.dp)
            .then(modifier)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.weight(1f)
        )
        if (action != null) {
            action()
        }
    }
}

@Composable
private fun PlanSelector(plans: List<Plan>, onSelectPlan: (Plan) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        plans.forEach { plan ->
            SelectorItem(text = plan.name, onClick = { onSelectPlan(plan) })
        }
    }
}

@Composable
private fun TrainingSelector(
    trainings: List<PlannedTraining>,
    onSelectTraining: (PlannedTraining) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        trainings.forEach { training ->
            SelectorItem(text = training.name, onClick = { onSelectTraining(training) })
        }
    }
}

@Composable
private fun SelectorItem(text: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun DateSelector(onSelectDate: (LocalDate) -> Unit) {
    val state = rememberDatePickerState()
    val currentOnSelectDate by rememberUpdatedState(onSelectDate)

    LaunchedEffect(state.selectedDateMillis) {
        state.selectedDateMillis?.let { millis ->
            val instant = Instant.fromEpochMilliseconds(millis)
            val timeZone = TimeZone.currentSystemDefault()
            val dateTime = instant.toLocalDateTime(timeZone)
            currentOnSelectDate(dateTime.date)
        }
    }

    DatePicker(
        state = state,
        modifier = Modifier.clip(RoundedCornerShape(12.dp))
    )
}

@Composable
private fun SelectedItem(text: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = text, style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
private fun StartButton(enabled: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp)
            .then(modifier),
        enabled = enabled
    ) {
        Text(text = "Start new training block")
    }
}

@PreviewLightDark
@Composable
private fun StartTrainingBlockScreen_LoadedPreview() {
    AppTheme {
        StartTrainingScreen(
            state = StartTrainingUiState(plans = samplePlans),
            onBack = {},
            onEditPlans = {},
            onSelectPlan = {},
            onResetPlan = {},
            onSelectTraining = {},
            onResetTraining = {},
            onSelectDate = {},
            onResetDate = {},
            onStart = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun StartTrainingBlockScreen_PlanSelectedPreview() {
    AppTheme {
        StartTrainingScreen(
            state = StartTrainingUiState(
                plans = samplePlans,
                selectedPlan = samplePlans.first()
            ),
            onBack = {},
            onEditPlans = {},
            onSelectPlan = {},
            onResetPlan = {},
            onSelectTraining = {},
            onResetTraining = {},
            onSelectDate = {},
            onResetDate = {},
            onStart = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun StartTrainingBlockScreen_TrainingSelectedPreview() {
    AppTheme {
        StartTrainingScreen(
            state = StartTrainingUiState(
                plans = samplePlans,
                selectedPlan = samplePlans.first(),
                selectedTraining = samplePlans.first().trainings.first()
            ),
            onBack = {},
            onEditPlans = {},
            onSelectPlan = {},
            onResetPlan = {},
            onSelectTraining = {},
            onResetTraining = {},
            onSelectDate = {},
            onResetDate = {},
            onStart = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun StartTrainingBlockScreen_DateSelectedPreview() {
    AppTheme {
        StartTrainingScreen(
            state = StartTrainingUiState(
                plans = samplePlans,
                selectedPlan = samplePlans.first(),
                selectedTraining = samplePlans.first().trainings.first(),
                selectedDate = LocalDate(year = 2024, monthNumber = 9, dayOfMonth = 9)
            ),
            onBack = {},
            onEditPlans = {},
            onSelectPlan = {},
            onResetPlan = {},
            onSelectTraining = {},
            onResetTraining = {},
            onSelectDate = {},
            onResetDate = {},
            onStart = {}
        )
    }
}
