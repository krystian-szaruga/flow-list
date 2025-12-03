package com.olr261dn.ui.compose.base

import androidx.compose.runtime.Composable

interface Template {
    @Composable
    fun Screen(
        title: String,
        showFabContent: Boolean = true,
        onTap: () -> Unit = {},
        onStatistics: () -> Unit = {},
        content: @Composable () -> Unit
    )
}