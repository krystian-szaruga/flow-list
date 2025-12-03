package com.olr261dn.ui.compose.constants

import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.olr261dn.ui.theme.customTheme

internal object Const {
    @Composable
    fun textFieldColors(): TextFieldColors {
        val theme = customTheme()
        return TextFieldDefaults.colors(
            errorTextColor = Color.Red,
            errorLabelColor = Color.Red,
            focusedContainerColor = theme.background,
            unfocusedContainerColor = theme.background.copy(alpha = 0.7f),
            focusedTextColor = theme.onBackground,
            unfocusedTextColor = theme.onBackground.copy(alpha = 0.7f),
            focusedLabelColor = theme.onBackground,
            unfocusedLabelColor = theme.onBackground.copy(alpha = 0.7f)
        )
    }
}
