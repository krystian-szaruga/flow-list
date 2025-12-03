package com.olr261dn.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

val Int.nonScaledSp: TextUnit
    @Composable
    get() = (this / LocalDensity.current.fontScale).sp

