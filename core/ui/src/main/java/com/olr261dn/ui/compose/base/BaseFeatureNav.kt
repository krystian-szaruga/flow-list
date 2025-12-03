package com.olr261dn.ui.compose.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import java.util.UUID

abstract class BaseFeatureNav {
    @Composable
    fun Nav(template: Template) {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = FeatureScreen.Main.route
        ) {
            composable(FeatureScreen.Main.route) {
                FeatureScreen(
                    template = template,
                    onClick = {
                        navController.navigate(FeatureScreen.Details.createRoute(it)) {
                            launchSingleTop = true
                        }
                    },
                    onStatistic = {
                        navController.navigate(FeatureScreen.Stats.route) {
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable(
                route = FeatureScreen.Details.route,
                arguments = listOf(navArgument("taskId") { type = NavType.StringType})
            ) { backStackEntry ->
                val taskId = backStackEntry.arguments?.getString("taskId") ?: return@composable
                val onBack = rememberPopBackSafely(navController)
                DetailScreen(UUID.fromString(taskId)) { onBack() }
            }
            composable(FeatureScreen.Stats.route) {
                val onBack = rememberPopBackSafely(navController)
                StatisticScreen { onBack() }
            }
        }
    }

    @Composable
    private fun rememberPopBackSafely(navController: NavController): () -> Unit {
        val isPopping = remember { mutableStateOf(false) }
        return remember {
            {
                if (!isPopping.value) {
                    isPopping.value = true
                    navController.popBackStack()
                }
            }
        }
    }

    @Composable
    protected abstract fun FeatureScreen(
        template: Template,
        onClick: (UUID) -> Unit,
        onStatistic: () -> Unit
    )

    @Composable
    protected abstract fun DetailScreen(taskId: UUID, onBackPress: () -> Unit)

    @Composable
    protected abstract fun StatisticScreen(onBackPress: () -> Unit)

    sealed class FeatureScreen(val route: String){
        data object Main: FeatureScreen("main")
        data object Stats: FeatureScreen("stats")
        data object Details: FeatureScreen("details/{taskId}") {
            fun createRoute(taskId: UUID) = "details/$taskId"
        }
    }
}