package com.maruchin.gymster.android.trainings.starttrainingblock

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.AssistChip
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
import com.maruchin.gymster.data.plans.model.samplePlans
import com.maruchin.gymster.data.trainings.model.TrainingBlock
import com.maruchin.gymster.feature.trainings.starttrainingblock.StartTrainingBlockUiState
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

// TODO Move it to trainings2 and use it to create new training

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun StartTrainingBlockScreen(
    state: StartTrainingBlockUiState,
    onBack: () -> Unit,
    onEditPlans: () -> Unit,
    onSelectPlan: (Plan) -> Unit,
    onResetPlan: () -> Unit,
    onSelectStartDate: (LocalDate) -> Unit,
    onResetStartDate: () -> Unit,
    onSelectWeeksDuration: (Int) -> Unit,
    onResetWeeksDuration: () -> Unit,
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
            onSelectStartDate = onSelectStartDate,
            onResetStartDate = onResetStartDate,
            onSelectWeeksDuration = onSelectWeeksDuration,
            onResetWeeksDuration = onResetWeeksDuration,
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
    state: StartTrainingBlockUiState,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    onEditPlans: () -> Unit,
    onSelectPlan: (Plan) -> Unit,
    onResetPlan: () -> Unit,
    onSelectStartDate: (LocalDate) -> Unit,
    onResetStartDate: () -> Unit,
    onSelectWeeksDuration: (Int) -> Unit,
    onResetWeeksDuration: () -> Unit,
    onStart: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = Modifier
            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
            .then(modifier),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            SelectorHeader(
                text = "Select plan",
                action = {
                    TextButton(onClick = onEditPlans) {
                        Text("Edit plans")
                    }
                }
            )
        }
        item {
            AnimatedContent(state.selectedPlan, label = "PlanSelector") { selectedPlan ->
                if (selectedPlan == null) {
                    PlanSelector(state = state, onSelectPlan = onSelectPlan)
                } else {
                    SelectedItem(text = selectedPlan.name, onClick = onResetPlan)
                }
            }
        }
        item {
            SelectorHeader(text = "Start date")
        }
        item {
            AnimatedContent(
                state.selectedStartDate,
                label = "StartDateSelector"
            ) { selectedStartDate ->
                if (selectedStartDate == null) {
                    StartDateSelector(onSelectDate = onSelectStartDate)
                } else {
                    SelectedItem(
                        text = selectedStartDate.formatFull(),
                        onClick = onResetStartDate
                    )
                }
            }
        }
        item {
            SelectorHeader(text = "Weeks duration")
        }
        item {
            AnimatedContent(
                state.selectedWeeksDuration,
                label = "WeeksDurationSelector"
            ) { selectedWeeksDuration ->
                if (selectedWeeksDuration == null) {
                    WeeksDurationSelector(
                        onSelectWeeksDuration = onSelectWeeksDuration
                    )
                } else {
                    SelectedItem(
                        text = "$selectedWeeksDuration weeks",
                        onClick = onResetWeeksDuration
                    )
                }
            }
        }
        item {
            StartButton(enabled = state.canCreate, onClick = onStart)
        }
    }
}

@Composable
private fun SelectorHeader(text: String, action: @Composable (() -> Unit)? = null) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 16.dp)) {
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
private fun PlanSelector(state: StartTrainingBlockUiState, onSelectPlan: (Plan) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        state.plans.forEach { plan ->
            PlanItem(plan = plan, onClick = { onSelectPlan(plan) })
        }
    }
}

@Composable
private fun PlanItem(plan: Plan, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Text(
            text = plan.name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun StartDateSelector(onSelectDate: (LocalDate) -> Unit) {
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun WeeksDurationSelector(onSelectWeeksDuration: (Int) -> Unit) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        TrainingBlock.possibleWeeksDurations.forEach { weeksDuration ->
            AssistChip(
                onClick = { onSelectWeeksDuration(weeksDuration) },
                label = {
                    Text(text = "$weeksDuration weeks")
                }
            )
        }
    }
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
            Icon(imageVector = Icons.Outlined.Check, contentDescription = null)
        }
    }
}

@Composable
private fun StartButton(enabled: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        enabled = enabled
    ) {
        Text(text = "Start new training block")
    }
}

@PreviewLightDark
@Composable
private fun StartTrainingBlockScreen_LoadedPreview() {
    AppTheme {
        StartTrainingBlockScreen(
            state = StartTrainingBlockUiState(
                plans = samplePlans,
                selectedPlan = null,
                selectedStartDate = null,
                selectedWeeksDuration = null,
                isCreated = false
            ),
            onBack = {},
            onEditPlans = {},
            onSelectPlan = {},
            onResetPlan = {},
            onSelectStartDate = {},
            onResetStartDate = {},
            onSelectWeeksDuration = {},
            onResetWeeksDuration = {},
            onStart = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun StartTrainingBlockScreen_PlanSelectedPreview() {
    AppTheme {
        StartTrainingBlockScreen(
            state = StartTrainingBlockUiState(
                plans = samplePlans,
                selectedPlan = samplePlans.first(),
                selectedStartDate = null,
                selectedWeeksDuration = null,
                isCreated = false
            ),
            onBack = {},
            onEditPlans = {},
            onSelectPlan = {},
            onResetPlan = {},
            onSelectStartDate = {},
            onResetStartDate = {},
            onSelectWeeksDuration = {},
            onResetWeeksDuration = {},
            onStart = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun StartTrainingBlockScreen_StartDateSelectedPreview() {
    AppTheme {
        StartTrainingBlockScreen(
            state = StartTrainingBlockUiState(
                plans = samplePlans,
                selectedPlan = samplePlans.first(),
                selectedStartDate = LocalDate(year = 2024, monthNumber = 9, dayOfMonth = 9),
                selectedWeeksDuration = null,
                isCreated = false
            ),
            onBack = {},
            onEditPlans = {},
            onSelectPlan = {},
            onResetPlan = {},
            onSelectStartDate = {},
            onResetStartDate = {},
            onSelectWeeksDuration = {},
            onResetWeeksDuration = {},
            onStart = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun StartTrainingBlockScreen_WeeksDurationSelectedPreview() {
    AppTheme {
        StartTrainingBlockScreen(
            state = StartTrainingBlockUiState(
                plans = samplePlans,
                selectedPlan = samplePlans.first(),
                selectedStartDate = LocalDate(year = 2024, monthNumber = 9, dayOfMonth = 9),
                selectedWeeksDuration = 8,
                isCreated = false
            ),
            onBack = {},
            onEditPlans = {},
            onSelectPlan = {},
            onResetPlan = {},
            onSelectStartDate = {},
            onResetStartDate = {},
            onSelectWeeksDuration = {},
            onResetWeeksDuration = {},
            onStart = {}
        )
    }
}
