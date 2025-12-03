package com.olr261dn.flowlist.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.olr261dn.flowlist.screens.LoadingScreen
import com.olr261dn.flowlist.screens.ScreenTemplate
import com.olr261dn.project.ProjectNav
import com.olr261dn.recurring.RecurringTaskNav
import com.olr261dn.settings.SettingsScreenView
import com.olr261dn.task.TaskNav
import com.olr261dn.viewmodel.DefaultScreenViewModel


internal object NavigationLauncher {
    @Composable
    fun Launcher() {
        val viewModel: DefaultScreenViewModel = hiltViewModel()
        val defaultScreen = viewModel.defaultScreen.collectAsState()
        val isLoaded = viewModel.isLoaded.collectAsState()
        val navigateToScreen = NavigateToScreen(
            navController = rememberNavController(),
            startScreen = Screens.Launch.name,
            updateLastScreen = { viewModel.setDefaultScreen(it) }
        )
        val template = ScreenTemplate(navigateToScreen)
        LaunchedEffect(isLoaded.value) {
            if (isLoaded.value) {
                navigateToScreen.navigateAndClearBackStack(
                    defaultScreen.value ?: Screens.DailyHabits.name
                )
            }
        }
        Navigation(navigateToScreen, template)
    }

    @Composable
    private fun Navigation(navigateToScreen: NavigateToScreen, template: ScreenTemplate) {
        NavHost(
            navController = navigateToScreen.navController,
            startDestination = navigateToScreen.startScreen
        ) {
            composable(Screens.Launch.name) { LoadingScreen() }
            composable(Screens.DailyHabits.name) { RecurringTaskNav.Nav(template) }
            composable(Screens.Tasks.name) { TaskNav.Nav(template) }
            composable(Screens.Goals.name) { ProjectNav.Nav(template) }
            composable(Screens.Settings.name) { SettingsScreenView.Screen(template) }
        }
    }
}