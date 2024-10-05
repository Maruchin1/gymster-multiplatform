package com.maruchin.gymster.android.trainings.trainingblocklist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.maruchin.gymster.android.ui.AppTheme
import com.maruchin.gymster.android.ui.formatMedium
import com.maruchin.gymster.data.trainings.model.TrainingBlock
import com.maruchin.gymster.data.trainings.model.sampleTrainingBlocks
import com.maruchin.gymster.feature.trainings.trainingblocklist.TrainingBlockListUiState

// TODO Hide FAB and show proper info when there is no training plans
// TODO Show empty content when there is no training blocks

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TrainingBlockListScreen(
    state: TrainingBlockListUiState,
    onBack: () -> Unit,
    onOpenTrainingBlock: (trainingBlockId: String) -> Unit,
    onStartTrainingBlock: () -> Unit
) {
    val listState = rememberLazyListState()
    val isScrolled by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex == 0
        }
    }
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            TopBar(onBack = onBack, scrollBehavior = topAppBarScrollBehavior)
        },
        floatingActionButton = {
            FloatingActionButton(isScrolled = isScrolled, onClick = onStartTrainingBlock)
        }
    ) { contentPadding ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
                .padding(contentPadding)
        ) {
            items(state.trainingBlocks) { trainingBlock ->
                TrainingBlockItem(
                    trainingBlock = trainingBlock,
                    onClick = { onOpenTrainingBlock(trainingBlock.id) }
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopBar(scrollBehavior: TopAppBarScrollBehavior, onBack: () -> Unit) {
    LargeTopAppBar(
        title = {
            Text(text = "Training blocks")
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

@Composable
private fun FloatingActionButton(isScrolled: Boolean, onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        icon = {
            Icon(
                imageVector = Icons.Outlined.FitnessCenter,
                contentDescription = null
            )
        },
        text = {
            Text(text = "New training block")
        },
        expanded = isScrolled
    )
}

@Composable
private fun TrainingBlockItem(trainingBlock: TrainingBlock, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth(), onClick = onClick) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = trainingBlock.planName, style = MaterialTheme.typography.titleLarge)
            Text(text = "${trainingBlock.weeks.size} weeks")
            Text(
                text = "${trainingBlock.startDate.formatMedium()} -" +
                    " ${trainingBlock.endDate.formatMedium()}"
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun TrainingBlockListScreen_LoadedPreview() {
    AppTheme {
        TrainingBlockListScreen(
            state = TrainingBlockListUiState(
                trainingBlocks = sampleTrainingBlocks
            ),
            onBack = {},
            onOpenTrainingBlock = {},
            onStartTrainingBlock = {}
        )
    }
}