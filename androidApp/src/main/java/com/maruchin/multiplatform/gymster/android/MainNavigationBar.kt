package com.maruchin.multiplatform.gymster.android

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.maruchin.gymster.android.ui.AppTheme
import com.maruchin.multiplatform.gymster.android.home.HomeGraph
import com.maruchin.multiplatform.gymster.android.trainingplans.TrainingPlansGraph

@Composable
internal fun MainNavigationBar(navController: NavController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoutes = backStackEntry?.destination?.hierarchy?.map { it.route }.orEmpty()
    NavigationBar {
        NavigationBarItem(
            selected = currentRoutes.contains(HomeGraph::class.qualifiedName),
            onClick = {
                navController.navigate(HomeGraph)
            },
            icon = {
                Icon(imageVector = Icons.Default.Home, contentDescription = null)
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = { /*TODO*/ },
            icon = {
                Icon(imageVector = Icons.Default.FitnessCenter, contentDescription = null)
            }
        )
        NavigationBarItem(
            selected = currentRoutes.contains(TrainingPlansGraph::class.qualifiedName),
            onClick = {
                navController.navigate(TrainingPlansGraph)
            },
            icon = {
                Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = null)
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = { /*TODO*/ },
            icon = {
                Icon(imageVector = Icons.Default.Person, contentDescription = null)
            }
        )
    }
}

@PreviewLightDark
@Composable
private fun MainNavigationBarPreview() {
    AppTheme {
        MainNavigationBar(navController = rememberNavController())
    }
}
