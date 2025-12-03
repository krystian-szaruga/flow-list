package com.olr261dn.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun customTheme(): ThemeColor {
    return when (isSystemInDarkTheme()) {
        true -> DarkThemeColor
        else -> LightThemeColor
    }
}


interface ThemeColor {
    val primary: Color
    val onPrimary: Color
    val secondary: Color
    val onSecondary: Color
    val redOnSecondary: Color
    val greenOnSecondary: Color
    val orangeOnSecondary: Color
        get() = Color(0xFFCC7E0C)
    val tertiary: Color
    val onTertiary: Color
    val selectedSurface: Color
    val onSelectedSurface: Color
    val background: Color
    val pureBackground: Color
    val onBackground: Color
    val surface : Color
    val onSurface : Color
    val container : Color
    val onContainer : Color

    fun getSurface(isSelected: Boolean): Color =
        if (!isSelected) surface else selectedSurface

    fun getOnSurface(isSelected: Boolean): Color =
        if (!isSelected) onSurface else onSelectedSurface

    fun getCompletedColor(isCompleted: Boolean) = if (isCompleted) greenOnSecondary else redOnSecondary
}

private object LightThemeColor : ThemeColor {
    override val primary: Color get() = Color(0x6658EE0A)
    override val onPrimary: Color get() = Color(0xFF070707)
    override val secondary: Color get() = Color(0xC9B5C57C)
    override val onSecondary: Color get() = Color(0xFF070707)
    override val redOnSecondary: Color get() = Color(0xFFB20101)
    override val greenOnSecondary: Color get() = Color(0xFF32A80B)
    override val tertiary: Color get() = Color(0xFFD3CBA0)
    override val selectedSurface: Color get() = Color(0xFF444030)
    override val onTertiary: Color get() = Color(0xFF0A0A0A)
    override val onSelectedSurface: Color get() = Color(0xFFFFFFFF)
    override val background: Color get() = Color(0xB5FFFFFF)
    override val pureBackground: Color get() = Color.White
    override val onBackground: Color get() = Color(0xFF070707)
    override val surface: Color get() = Color(0x6ACEF18C)
    override val onSurface: Color get() = Color(0xFF1A1717)
    override val container: Color get() = Color(0xFF1A1C19)
    override val onContainer: Color get() = Color(0xFFFAF7F7)
}

private object DarkThemeColor : ThemeColor {
    override val primary: Color get() = Color(0xFF011A10)
    override val onPrimary: Color get() = Color(0xFFFFFFFF)
    override val secondary: Color get() = Color(0xFF192607)
    override val onSecondary: Color get() = Color(0xFFFFFFFF)
    override val redOnSecondary: Color get() = Color(0xE9F10000)
    override val greenOnSecondary: Color get() = Color(0xFF44DE0B)
    override val tertiary: Color get() = Color(0xFF171801)
    override val selectedSurface: Color get() = Color(0xFFD8EA10)
    override val onTertiary: Color get() = Color(0xFFF2F5ED)
    override val onSelectedSurface: Color get() = Color(0xFF000000)
    override val background: Color get() = Color(0x81000000)
    override val pureBackground: Color get() = Color.Black
    override val onBackground: Color get() = Color(0xFFFFFFFF)
    override val surface: Color get() = Color(0xA4010E0E)
    override val onSurface: Color get() = Color(0xFFFFFFFF)
    override val container: Color get() = Color(0xFFCEF3D5)
    override val onContainer: Color get() = Color(0xFF0A0A0A)
}
