package com.maruchin.gymster.android.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.maruchin.gymster.android.ui.AppTheme
import com.maruchin.gymster.data.trainings.model.TrainingBlock
import com.maruchin.gymster.data.trainings.model.sampleActiveTrainingBlock

// TODO Show current week based on the current date

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun CurrentWeekTrainingsCard(
    trainingBlock: TrainingBlock,
    modifier: Modifier = Modifier,
    onOpenTraining: (trainingBlockId: String, weekIndex: Int, trainingIndex: Int) -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.CalendarMonth,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Week ${trainingBlock.currentWeekNumber}",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            FlowRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                trainingBlock.currentWeek.trainings.forEachIndexed { index, training ->
                    FilterChip(
                        selected = training.isComplete,
                        onClick = {
                            onOpenTraining(trainingBlock.id, trainingBlock.currentWeekIndex, index)
                        },
                        label = {
                            Text(text = training.name)
                        },
                        modifier = Modifier.weight(1f),
                        leadingIcon = if (training.isComplete) {
                            {
                                Icon(
                                    imageVector = Icons.Rounded.Check,
                                    contentDescription = null
                                )
                            }
                        } else {
                            null
                        }
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun CurrentWeekTrainingsCardPreview() {
    AppTheme {
        CurrentWeekTrainingsCard(
            trainingBlock = sampleActiveTrainingBlock,
            onOpenTraining = { _, _, _ -> }
        )
    }
}
