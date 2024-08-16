package com.maruchin.gymster.android.planlist.planlist

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Dataset
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.maruchin.gymster.android.ui.AppTheme
import com.maruchin.gymster.android.ui.EmptyContent
import com.maruchin.gymster.android.ui.LoadingContent
import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.plans.model.samplePlans
import com.maruchin.gymster.planlist.planlist.PlanListUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PlanListScreen(
    state: PlanListUiState,
    onBack: () -> Unit,
    onAddPlan: () -> Unit,
    onSeedPlans: () -> Unit,
    onStartTrainingBlock: (planId: String) -> Unit,
    onEditPlan: (String) -> Unit
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            TopBar(
                scrollBehavior = topAppBarScrollBehavior,
                onBack = onBack,
                onAddPlan = onAddPlan,
                onSeedPlans = onSeedPlans
            )
        }
    ) { contentPadding ->
        AnimatedContent(
            targetState = state,
            contentKey = { it::class },
            label = "PlanListScreenAnimatedContent",
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) { targetState ->
            when (targetState) {
                PlanListUiState.Loading -> LoadingContent()
                PlanListUiState.Empty -> EmptyContent(text = "No training plans yet")
                is PlanListUiState.Loaded -> LoadedContent(
                    plans = targetState.plans,
                    scrollBehavior = topAppBarScrollBehavior,
                    onStartNewTrainingBlock = onStartTrainingBlock,
                    onEditPlan = onEditPlan
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onBack: () -> Unit,
    onSeedPlans: () -> Unit,
    onAddPlan: () -> Unit
) {
    LargeTopAppBar(
        title = {
            Text("Training plans")
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = onSeedPlans) {
                Icon(imageVector = Icons.Default.Dataset, contentDescription = null)
            }
            IconButton(onClick = onAddPlan) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoadedContent(
    plans: List<Plan>,
    scrollBehavior: TopAppBarScrollBehavior,
    onStartNewTrainingBlock: (planId: String) -> Unit,
    onEditPlan: (planId: String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        items(plans) { plan ->
            PlanItem(
                name = plan.name,
                onStartNewTrainingBlock = { onStartNewTrainingBlock(plan.id) },
                onEditPlan = { onEditPlan(plan.id) }
            )
        }
    }
}

@Composable
private fun PlanItem(name: String, onStartNewTrainingBlock: () -> Unit, onEditPlan: () -> Unit) {
    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(12.dp)
        )
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AssistChip(
                onClick = onStartNewTrainingBlock,
                label = {
                    Text(text = "New training block")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.FitnessCenter,
                        contentDescription = null,
                        modifier = Modifier.size(AssistChipDefaults.IconSize)
                    )
                }
            )
            AssistChip(
                onClick = onEditPlan,
                label = {
                    Text(text = "Edit plan")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        modifier = Modifier.size(AssistChipDefaults.IconSize)
                    )
                }
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun PlanListScreen_LoadedPreview() {
    AppTheme {
        PlanListScreen(
            state = PlanListUiState.Loaded(plans = samplePlans),
            onBack = {},
            onAddPlan = {},
            onSeedPlans = {},
            onStartTrainingBlock = {},
            onEditPlan = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun PlanListScreen_LoadingPreview() {
    AppTheme {
        PlanListScreen(
            state = PlanListUiState.Loading,
            onBack = {},
            onAddPlan = {},
            onSeedPlans = {},
            onStartTrainingBlock = {},
            onEditPlan = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun PlanListScreen_EmptyPreview() {
    AppTheme {
        PlanListScreen(
            state = PlanListUiState.Empty,
            onBack = {},
            onAddPlan = {},
            onSeedPlans = {},
            onStartTrainingBlock = {},
            onEditPlan = {}
        )
    }
}
