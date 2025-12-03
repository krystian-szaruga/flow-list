package com.olr261dn.flowlist.navigation

import android.util.Log
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder

internal class NavigateToScreen(
    val navController: NavHostController,
    val startScreen: String,
    private val updateLastScreen: (String) -> Unit,
) {
    private var screenToNavigate = startScreen

    fun navigateAndClearBackStack(destinationScreen: String?) {
        destinationScreen?.let {
            setDestinationScreen(it)
            navigateToScreen {
                popUpTo(0) {
                    inclusive = true
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        } ?: Log.w("NavigateToScreen", "Destination screen is null, navigation skipped.")
    }

    private fun setDestinationScreen(destinationScreens: String?) {
        destinationScreens?.let {
            updateLastScreen(it)
            screenToNavigate = it
        }
    }

    private fun navigateToScreen(builder: NavOptionsBuilder.() -> Unit) {
        navController.navigate(screenToNavigate) {
            builder()
        }
    }
}
