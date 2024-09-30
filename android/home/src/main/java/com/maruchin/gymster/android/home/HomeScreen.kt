package com.maruchin.gymster.android.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ListAlt
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.maruchin.gymster.android.ui.AppTheme
import com.maruchin.gymster.data.trainings.model.sampleActiveTrainingBlock
import com.maruchin.gymster.feature.home.home.HomeUiState
import com.maruchin.gymster.feature.home.home.HomeViewModel

@Composable
internal fun HomeScreen(
    onOpenPlans: () -> Unit,
    onOpenActiveTrainingBlock: () -> Unit,
    onOpenTrainings: () -> Unit,
    viewModel: HomeViewModel = viewModel { HomeViewModel() }
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onOpenPlans = onOpenPlans,
        onOpenActiveTrainingBlock = onOpenActiveTrainingBlock,
        onOpenTrainings = onOpenTrainings
    )
}

@Composable
private fun HomeScreen(
    state: HomeUiState,
    onOpenPlans: () -> Unit,
    onOpenActiveTrainingBlock: () -> Unit,
    onOpenTrainings: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBar()
        }
    ) { contentPadding ->
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier.padding(contentPadding),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 16.dp
        ) {
            state.activeTrainingBlock?.let { activeTrainingBlock ->
                item(span = StaggeredGridItemSpan.FullLine) {
                    ActiveTrainingBlockCard(
                        trainingBlock = activeTrainingBlock,
                        onOpenActive = onOpenActiveTrainingBlock,
                        onViewAll = onOpenTrainings,
                        modifier = Modifier.animateItem()
                    )
                }
            }
            item {
                Card(onClick = onOpenPlans) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ListAlt,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Plans",
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            item {
                Card(onClick = onOpenTrainings) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.FitnessCenter,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Trainings",
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
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

@PreviewLightDark
@Composable
private fun HomeScreen_LoadedPreview() {
    AppTheme {
        HomeScreen(
            state = HomeUiState(activeTrainingBlock = sampleActiveTrainingBlock),
            onOpenPlans = {},
            onOpenTrainings = {},
            onOpenActiveTrainingBlock = {}
        )
    }
}
