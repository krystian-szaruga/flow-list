package com.olr261dn.flowlist.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.olr261dn.domain.model.NavbarPosition
import com.olr261dn.flowlist.navigation.NavigateToScreen
import com.olr261dn.flowlist.navigation.Screens
import com.olr261dn.ui.compose.base.Template
import com.olr261dn.ui.theme.customTheme
import com.olr261dn.viewmodel.PreferencesViewModel

internal class ScreenTemplate(private val navigateToScreen: NavigateToScreen) : Template {
    @Composable
    override fun Screen(
        title: String,
        showFabContent: Boolean,
        onTap: () -> Unit,
        onStatistics: () -> Unit,
        content: @Composable (() -> Unit),
    ) {
        val navBarViewModel: PreferencesViewModel = hiltViewModel()
        val navbarPosition by navBarViewModel.navBarPosition.collectAsState()
        val quickActionPosition by navBarViewModel.quickActionPosition.collectAsState()

        Scaffold(
            bottomBar = {
                if (navbarPosition == NavbarPosition.BOTTOM)
                    NavigationBar(navigateToScreen)
            },
            topBar = {
                if (navbarPosition == NavbarPosition.TOP)
                    TopBar(navigateToScreen)
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                content()
                quickActionPosition?.let { position ->
                    if (showFabContent) {
                        FabContent(
                            quickActionPosition = position,
                            onAddTap = onTap,
                            onStatisticsTap = onStatistics,
                            onSettings = {
                                navigateToScreen.navigateAndClearBackStack(Screens.Settings.name)
                            }
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun TopBar(navigateToScreen: NavigateToScreen, initialScrollIndex: Int = 0) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(customTheme().primary)
        ) {
            Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
            NavigationBar(navigateToScreen, initialScrollIndex)
        }
    }
}
