package com.maruchin.gymster.android.planeditor.planeditor

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.maruchin.gymster.android.ui.AppTheme
import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.plans.model.samplePlans

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun TopBar(
    plan: Plan?,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    onBack: () -> Unit,
    onChangePlanName: () -> Unit,
    onDeletePlan: () -> Unit
) {
    TopAppBar(
        title = {
            Text(plan?.name.orEmpty())
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
        },
        actions = {
            var isMenuExpanded by rememberSaveable { mutableStateOf(false) }
            Box {
                IconButton(onClick = { isMenuExpanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = null)
                }
                DropdownMenu(
                    expanded = isMenuExpanded,
                    onDismissRequest = { isMenuExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = {
                            Text("Change plan name")
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Edit, contentDescription = null)
                        },
                        onClick = {
                            isMenuExpanded = false
                            onChangePlanName()
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text("Delete plan")
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Delete, contentDescription = null)
                        },
                        onClick = {
                            isMenuExpanded = false
                            onDeletePlan()
                        }
                    )
                }
            }
        },
        scrollBehavior = topAppBarScrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
private fun TopBarPreview() {
    AppTheme {
        TopBar(
            plan = samplePlans.first(),
            topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(),
            onBack = {},
            onChangePlanName = {},
            onDeletePlan = {}
        )
    }
}
