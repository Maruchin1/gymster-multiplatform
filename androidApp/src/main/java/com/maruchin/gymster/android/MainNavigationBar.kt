package com.maruchin.gymster.android

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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.maruchin.gymster.android.home.HomeGraph
import com.maruchin.gymster.android.plans.PlansGraph
import com.maruchin.gymster.android.trainings.TrainingsGraph
import com.maruchin.gymster.android.ui.AppTheme

@Composable
internal fun MainNavigationBar(navController: NavController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoutes = backStackEntry?.destination?.hierarchy?.map { it.route }.orEmpty()
    NavigationBar {
        NavigationBarItem(
            selected = currentRoutes.contains(HomeGraph::class.qualifiedName),
            onClick = {
                navController.navigateToItem(HomeGraph)
            },
            icon = {
                Icon(imageVector = Icons.Default.Home, contentDescription = null)
            }
        )
        NavigationBarItem(
            selected = currentRoutes.contains(TrainingsGraph::class.qualifiedName),
            onClick = {
                navController.navigateToItem(TrainingsGraph)
            },
            icon = {
                Icon(imageVector = Icons.Default.FitnessCenter, contentDescription = null)
            }
        )
        NavigationBarItem(
            selected = currentRoutes.contains(PlansGraph::class.qualifiedName),
            onClick = {
                navController.navigateToItem(PlansGraph)
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

private fun NavController.navigateToItem(item: Any) {
    navigate(item) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

@PreviewLightDark
@Composable
private fun MainNavigationBarPreview() {
    AppTheme {
        MainNavigationBar(navController = rememberNavController())
    }
}
