package com.maruchin.gymster.android.trainings.trainingblocklist

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.maruchin.gymster.android.ui.AppTheme
import com.maruchin.gymster.android.ui.EmptyContent
import com.maruchin.gymster.android.ui.LoadingContent
import com.maruchin.gymster.data.trainings.model.sampleTrainingBlocks
import com.maruchin.gymster.feature.trainings.trainingblocklist.TrainingBlockListUiState

@Composable
internal fun TrainingBlockListScreen(
    state: TrainingBlockListUiState,
    onAddTrainingBlock: () -> Unit,
    onOpenTrainingBlock: (trainingBlockId: String) -> Unit
) {
    Scaffold(
        topBar = {
            TopBar()
        },
        floatingActionButton = {
            AddTrainingBlockButton(onClick = onAddTrainingBlock)
        }
    ) { contentPadding ->
        AnimatedContent(
            targetState = state,
            contentKey = { it::class },
            label = "TrainingBlockListScreenAnimatedContent",
            modifier = Modifier.padding(contentPadding)
        ) {
            when (it) {
                TrainingBlockListUiState.Loading -> LoadingContent()
                TrainingBlockListUiState.Empty -> EmptyContent(text = "No trainings")
                is TrainingBlockListUiState.Loaded -> LoadedContent(
                    state = it,
                    onOpenTrainingBlock = onOpenTrainingBlock
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(text = "Trainings")
        }
    )
}

@Composable
private fun AddTrainingBlockButton(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Icon(imageVector = Icons.Default.Add, contentDescription = null)
    }
}

@Composable
private fun LoadedContent(
    state: TrainingBlockListUiState.Loaded,
    onOpenTrainingBlock: (trainingBlockId: String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(state.trainingBlocks) { trainingBlock ->
            ElevatedCard(
                onClick = { onOpenTrainingBlock(trainingBlock.id) },
                modifier = Modifier.fillParentMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(text = trainingBlock.planName, style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Duration: ${trainingBlock.weeks.size} weeks",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun TrainingBlockListScreen_LoadedPreview() {
    AppTheme {
        TrainingBlockListScreen(
            state = TrainingBlockListUiState.Loaded(sampleTrainingBlocks),
            onAddTrainingBlock = {},
            onOpenTrainingBlock = {}
        )
    }
}
