package com.openclassrooms.p12m_joiefull.ui.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable

/**
 * Manages the navigation flow for the application's UI, adapting to different screen sizes.
 *
 * This composable function determines the appropriate navigation setup based on the
 * [WindowSizeClass]. It adjusts the UI for different screen sizes:
 * - Compact and Medium widths (e.g., smartphones and small tablets).
 * - Expanded widths (e.g., larger tablets or desktop environments).
 *
 * @param windowSizeClass The [WindowSizeClass] defining the width and height size classes of the screen.
 */
@Composable
fun Navigation(windowSizeClass: WindowSizeClass) {

    // Check the window size class to adjust UI for different screen sizes
    when (windowSizeClass.widthSizeClass) {

        // Compact screens (e.g., smartphones in portrait mode)
        WindowWidthSizeClass.Compact -> {
            SetupNavForSmartphone(widthSizeClass = WindowWidthSizeClass.Compact)
            }

        // Medium and Expanded screens (e.g., tablets or landscape mode)
        WindowWidthSizeClass.Medium, WindowWidthSizeClass.Expanded -> {
            SetupNavForTablet(widthSizeClass = WindowWidthSizeClass.Expanded)
        }

        /// Default fallback (e.g., undefined cases)
        else -> {
            SetupNavForSmartphone(widthSizeClass = WindowWidthSizeClass.Compact)
        }
    }

}
