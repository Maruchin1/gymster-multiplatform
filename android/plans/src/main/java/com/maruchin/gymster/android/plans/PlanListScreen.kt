package com.maruchin.gymster.android.plans

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Dataset
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.maruchin.gymster.android.ui.AppTheme
import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.plans.model.samplePlans
import com.maruchin.gymster.feature.plans.planlist.PlanListUiState
import com.maruchin.gymster.feature.plans.planlist.PlanListViewModel

@Composable
internal fun PlanListScreen(
    onBack: () -> Unit,
    onOpenPlan: (planId: String) -> Unit,
    viewModel: PlanListViewModel = viewModel { PlanListViewModel() }
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    PlanListScreen(
        state = state,
        onBack = onBack,
        onSeedPlans = viewModel::seedPlans,
        onOpenPlan = onOpenPlan,
        onCreatePlan = viewModel::createPlan
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlanListScreen(
    state: PlanListUiState,
    onBack: () -> Unit,
    onSeedPlans: () -> Unit,
    onOpenPlan: (planId: String) -> Unit,
    onCreatePlan: (name: String) -> Unit
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            TopBar(
                scrollBehavior = topAppBarScrollBehavior,
                onBack = onBack,
                onSeedPlans = onSeedPlans
            )
        }
    ) { contentPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
                .padding(contentPadding),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state.plans, key = { it.id }) { plan ->
                PlanItem(plan = plan, onClick = { onOpenPlan(plan.id) })
            }
            item {
                CreatePlanItem(onCreatePlan = onCreatePlan)
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onBack: () -> Unit,
    onSeedPlans: () -> Unit
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
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun LazyGridItemScope.PlanItem(plan: Plan, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .animateItem(),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = plan.name,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun LazyGridItemScope.CreatePlanItem(onCreatePlan: (name: String) -> Unit) {
    var isAddingPlan by rememberSaveable { mutableStateOf(false) }

    OutlinedCard(
        onClick = { isAddingPlan = true },
        modifier = Modifier
            .aspectRatio(1f)
            .animateItem()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
        }
    }

    if (isAddingPlan) {
        PlanFormModal(
            plan = null,
            onDismiss = { isAddingPlan = false },
            onSave = onCreatePlan
        )
    }
}

@PreviewLightDark
@Composable
private fun PlanListScreenPreview() {
    AppTheme {
        PlanListScreen(
            state = PlanListUiState(plans = samplePlans),
            onBack = {},
            onSeedPlans = {},
            onOpenPlan = {},
            onCreatePlan = {}
        )
    }
}
