package com.olr261dn.ui.compose.base

import androidx.compose.runtime.Composable
import java.util.UUID

interface DetailScreenView {
    @Composable
    fun Content(id: UUID, onBackPress: () -> Unit)
}