package com.maruchin.gymster.android

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
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
        val isHomeSelected = currentRoutes.contains(HomeGraph::class.qualifiedName)
        val isTrainingsSelected = currentRoutes.contains(TrainingsGraph::class.qualifiedName)
        val isPlansSelected = currentRoutes.contains(PlansGraph::class.qualifiedName)
        val isProfileSelected = false

        NavigationBarItem(
            selected = isHomeSelected,
            onClick = {
                navController.navigateToItem(HomeGraph)
            },
            icon = {
                Icon(
                    imageVector = if (isHomeSelected) Icons.Default.Home else Icons.Outlined.Home,
                    contentDescription = null
                )
            },
            label = {
                Text(text = "Home")
            }
        )
        NavigationBarItem(
            selected = isTrainingsSelected,
            onClick = {
                navController.navigateToItem(TrainingsGraph)
            },
            icon = {
                Icon(
                    imageVector = if (isTrainingsSelected) Icons.Default.FitnessCenter else Icons.Outlined.FitnessCenter,
                    contentDescription = null
                )
            },
            label = {
                Text(text = "Trainings")
            }
        )
        NavigationBarItem(
            selected = isPlansSelected,
            onClick = {
                navController.navigateToItem(PlansGraph)
            },
            icon = {
                Icon(
                    imageVector = if (isPlansSelected) Icons.Default.CalendarMonth else Icons.Outlined.CalendarMonth,
                    contentDescription = null
                )
            },
            label = {
                Text(text = "Plans")
            }
        )
        NavigationBarItem(
            selected = isProfileSelected,
            onClick = { /*TODO*/ },
            icon = {
                Icon(
                    imageVector = if (isProfileSelected) Icons.Default.Person else Icons.Outlined.Person,
                    contentDescription = null
                )
            },
            label = {
                Text(text = "Profile")
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
