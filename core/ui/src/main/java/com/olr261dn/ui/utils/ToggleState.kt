package com.olr261dn.ui.utils

import androidx.compose.runtime.MutableState

fun MutableState<Boolean>.toggle() {
    value = !value
}