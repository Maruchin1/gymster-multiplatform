package com.maruchin.gymster.android.home.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.maruchin.gymster.android.ui.formatMedium
import com.maruchin.gymster.data.trainings.model.sampleTrainingBlocks
import com.maruchin.gymster.feature.home.home.HomeUiState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate

@Composable
internal fun HomeScreen(
    state: HomeUiState,
    onOpenPlans: () -> Unit,
    onOpenTrainingBlock: (trainingBlockId: String) -> Unit
) {
    Scaffold(
        topBar = {
            TopBar()
        },
        bottomBar = {
            BottomBar(onOpenPlans = onOpenPlans)
        }
    ) { contentPadding ->
        AnimatedContent(
            targetState = state,
            contentKey = { it::class },
            label = "HomeScreenAnimatedContent",
            modifier = Modifier.padding(contentPadding)
        ) {
            when (it) {
                HomeUiState.Loading -> LoadingContent()
                HomeUiState.Empty -> EmptyContent(text = "No trainings yet")
                is HomeUiState.Loaded -> LoadedContent(
                    state = it,
                    onOpenTrainingBlock = onOpenTrainingBlock
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text("Gymster")
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null)
            }
        }
    )
}

@Composable
private fun BottomBar(onOpenPlans: () -> Unit) {
    BottomAppBar(
        actions = {
            IconButton(onClick = onOpenPlans) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ListAlt,
                    contentDescription = null
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.TrendingUp,
                    contentDescription = null
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.FitnessCenter, contentDescription = null)
            }
        }
    )
}

@Composable
private fun LoadedContent(
    state: HomeUiState.Loaded,
    onOpenTrainingBlock: (trainingBlockId: String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(state.trainingBlocks) { trainingBlock ->
            TrainingBlockItem(
                name = trainingBlock.planName,
                startDate = trainingBlock.startDate,
                endDate = trainingBlock.endDate,
                onClick = { onOpenTrainingBlock(trainingBlock.id) }
            )
        }
    }
}

@Composable
private fun TrainingBlockItem(
    name: String,
    startDate: LocalDate,
    endDate: LocalDate,
    onClick: () -> Unit
) {
    ElevatedCard(modifier = Modifier.fillMaxWidth(), onClick = onClick) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = name, style = MaterialTheme.typography.titleLarge)
            Text(
                text = "Start: ${startDate.toJavaLocalDate().formatMedium()}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "End: ${endDate.toJavaLocalDate().formatMedium()}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun HomeScreen_LoadedPreview() {
    AppTheme {
        HomeScreen(
            state = HomeUiState.Loaded(sampleTrainingBlocks),
            onOpenPlans = {},
            onOpenTrainingBlock = {}
        )
    }
}
