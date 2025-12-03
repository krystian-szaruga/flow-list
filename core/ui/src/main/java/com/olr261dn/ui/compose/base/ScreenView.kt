package com.olr261dn.ui.compose.base

import androidx.compose.runtime.Composable
import java.util.UUID

interface ScreenView {
    @Composable
    fun Content(
        title: String,
        screenTemplate: Template,
        onDetailClick: (UUID) -> Unit,
        onStatistic: () -> Unit
    )
}
