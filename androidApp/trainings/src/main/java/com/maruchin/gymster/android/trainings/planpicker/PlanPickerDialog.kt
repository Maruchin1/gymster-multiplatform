package com.maruchin.gymster.android.trainings.planpicker

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.maruchin.gymster.android.ui.AppTheme
import com.maruchin.gymster.android.ui.LoadingContent
import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.plans.model.PlanDay
import com.maruchin.gymster.data.plans.model.samplePlans
import com.maruchin.gymster.feature.trainings.planpicker.PlanPickerUiState
import kotlinx.datetime.LocalDate

@Composable
internal fun PlanPickerDialog(
    state: PlanPickerUiState,
    onClose: () -> Unit,
    onSelectPlan: (Plan) -> Unit,
    onSelectDay: (PlanDay) -> Unit,
    onSelectDate: (Long) -> Unit,
    onStartTraining: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(onClose = onClose)
        }
    ) { contentPadding ->
        AnimatedContent(
            targetState = state,
            contentKey = { it::class },
            label = "PlanPickerDialogAnimatedContent",
            modifier = Modifier.padding(contentPadding)
        ) { targetState ->
            when (targetState) {
                PlanPickerUiState.Loading -> LoadingContent()
                is PlanPickerUiState.Loaded -> LoadedContent(
                    state = targetState,
                    onSelectPlan = onSelectPlan,
                    onSelectDay = onSelectDay,
                    onSelectDate = onSelectDate,
                    onStartTraining = onStartTraining
                )
            }
        }
    }
}

@Composable
private fun LoadedContent(
    state: PlanPickerUiState.Loaded,
    onSelectPlan: (Plan) -> Unit,
    onSelectDay: (PlanDay) -> Unit,
    onSelectDate: (Long) -> Unit,
    onStartTraining: () -> Unit
) {
    Column {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item(key = "plan_header") {
                StepHeader(title = "Plan", selected = state.selectedPlan?.name)
            }
            if (state.selectedPlan == null) {
                items(state.plans, key = { it.id }) { plan ->
                    StepItem(text = plan.name, onClick = { onSelectPlan(plan) })
                }
            }
            if (state.selectedPlan != null) {
                item(key = "training_header") {
                    StepHeader(title = "Training", selected = state.selectedDay?.name)
                }
                if (state.selectedDay == null) {
                    items(state.days, key = { it.id }) { day ->
                        StepItem(text = day.name, onClick = { onSelectDay(day) })
                    }
                }
                if (state.selectedDay != null) {
                    item {
                        DatePickerItem(
                            selectedDateMillis = state.selectedDateMillis,
                            onSelectDate = onSelectDate
                        )
                    }
                }
            }
        }
        Button(
            onClick = onStartTraining,
            enabled = state.canStartTraining,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Start training")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(onClose: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = "New training")
        },
        navigationIcon = {
            IconButton(onClick = onClose) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
        }
    )
}

@Composable
private fun LazyItemScope.StepHeader(title: String, selected: String?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateItem()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                AnimatedVisibility(visible = selected != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null)
                }
            }
            AnimatedVisibility(visible = selected != null) {
                Text(
                    text = selected!!,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun LazyItemScope.StepItem(text: String, onClick: () -> Unit) {
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .animateItem()
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun DatePickerItem(selectedDateMillis: Long, onSelectDate: (Long) -> Unit) {
    val state = rememberDatePickerState(initialSelectedDateMillis = selectedDateMillis)

    LaunchedEffect(state.selectedDateMillis) {
        state.selectedDateMillis?.let(onSelectDate)
    }

    DatePicker(state = state)
}

@PreviewLightDark
@Composable
private fun TrainingPickerDialog_InitialPreview() {
    AppTheme {
        PlanPickerDialog(
            state = PlanPickerUiState.Loaded(
                plans = samplePlans,
                selectedPlan = null,
                selectedDay = null,
                selectedDate = LocalDate(year = 2024, monthNumber = 8, dayOfMonth = 1)
            ),
            onClose = {},
            onSelectPlan = {},
            onSelectDay = {},
            onSelectDate = {},
            onStartTraining = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun TrainingPickerDialog_PlanSelectedPreview() {
    AppTheme {
        PlanPickerDialog(
            state = PlanPickerUiState.Loaded(
                plans = samplePlans,
                selectedPlan = samplePlans.first(),
                selectedDay = null,
                selectedDate = LocalDate(year = 2024, monthNumber = 8, dayOfMonth = 1)
            ),
            onClose = {},
            onSelectPlan = {},
            onSelectDay = {},
            onSelectDate = {},
            onStartTraining = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun TrainingPickerDialog_DaySelectedPreview() {
    AppTheme {
        PlanPickerDialog(
            state = PlanPickerUiState.Loaded(
                plans = samplePlans,
                selectedPlan = samplePlans.first(),
                selectedDay = samplePlans.first().days.first(),
                selectedDate = LocalDate(year = 2024, monthNumber = 8, dayOfMonth = 1)
            ),
            onClose = {},
            onSelectPlan = {},
            onSelectDay = {},
            onSelectDate = {},
            onStartTraining = {}
        )
    }
}
