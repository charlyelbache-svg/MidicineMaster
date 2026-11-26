package com.medicinemaster.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.medicinemaster.app.ui.screens.HomeScreen
import com.medicinemaster.app.ui.screens.ProgressScreen
import com.medicinemaster.app.ui.screens.SettingsScreen
import com.medicinemaster.app.ui.screens.StudyScreen
import com.medicinemaster.app.ui.screens.SubjectsScreen

private data class BottomDestination(
    val route: String,
    val label: String,
    val icon: ImageVector
)

private val bottomDestinations = listOf(
    BottomDestination(Routes.HOME, "Inicio", Icons.Outlined.Home),
    BottomDestination(Routes.SUBJECTS, "Materias", Icons.Outlined.MenuBook),
    BottomDestination(Routes.STUDY, "Estudiar", Icons.Outlined.Mic),
    BottomDestination(Routes.PROGRESS, "Progreso", Icons.Outlined.TrendingUp),
    BottomDestination(Routes.SETTINGS, "Ajustes", Icons.Outlined.Settings)
)

@Composable
fun MedicineMasterApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    Scaffold(
        containerColor = androidx.compose.material3.MaterialTheme.colorScheme.background,
        bottomBar = {
            NavigationBar(
                containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface,
                contentColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
            ) {
                bottomDestinations.forEach { destination ->
                    val selected = currentDestination?.hierarchy?.any {
                        it.route == destination.route
                    } == true
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navController.navigate(destination.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(destination.icon, contentDescription = destination.label) },
                        label = { Text(destination.label) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                            selectedTextColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                            indicatorColor = androidx.compose.material3.MaterialTheme.colorScheme.surfaceVariant,
                            unselectedIconColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.HOME) {
                HomeScreen(
                    onOpenSubjects = { navController.navigate(Routes.SUBJECTS) },
                    onOpenStudy = { navController.navigate(Routes.STUDY) },
                    onOpenProgress = { navController.navigate(Routes.PROGRESS) },
                    onOpenSettings = { navController.navigate(Routes.SETTINGS) }
                )
            }
            composable(Routes.SUBJECTS) {
                SubjectsScreen(onStartSubject = { subjectId ->
                    navController.navigate("study/$subjectId")
                })
            }
            composable(Routes.STUDY) {
                StudyScreen()
            }
            composable(
                route = Routes.STUDY_SUBJECT,
                arguments = listOf(navArgument("subjectId") { type = NavType.StringType })
            ) { entry ->
                StudyScreen(initialSubjectId = entry.arguments?.getString("subjectId") ?: "all")
            }
            composable(Routes.PROGRESS) {
                ProgressScreen()
            }
            composable(Routes.SETTINGS) {
                SettingsScreen()
            }
        }
    }
}
