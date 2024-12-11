package com.openclassrooms.p12m_joiefull.ui.theme

import androidx.compose.ui.graphics.Color

data class ExtendedColors(
    val orange: Color
)

val LocalExtendedColors = androidx.compose.runtime.staticCompositionLocalOf {
    ExtendedColors(
        orange = Orange
    )
}
