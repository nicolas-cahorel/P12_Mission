package com.openclassrooms.p12m_joiefull.ui.navigation

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable

/**
 * Manages the navigation flow for the application's UI, adapting to different screen sizes and orientations.
 *
 * This composable function determines the appropriate navigation setup based on the [WindowSizeClass].
 * It adjusts the UI based on both the width and height size classes:
 * - Compact and Medium width sizes (e.g., smartphones and small tablets).
 * - Expanded width sizes (e.g., larger tablets or desktop environments).
 *
 * Additionally, the screen orientation (height size class) is checked to provide a suitable layout:
 * - Medium or Expanded height size classes indicate a larger screen, such as tablets in portrait or landscape mode.
 * - Default fallback is used for smaller screens like smartphones.
 *
 * @param windowSizeClass The [WindowSizeClass] defining the width and height size classes of the screen.
 * This parameter is used to determine the appropriate layout for the UI.
 */
@Composable
fun Navigation(windowSizeClass: WindowSizeClass) {

    // Extract the width size class from the window size class
    val screenWidth = windowSizeClass.widthSizeClass

    // Extract the height size class from the window size class
    val screenHeight = windowSizeClass.heightSizeClass

    // Adjust navigation based on the screen width and height size classes
    if (screenWidth == WindowWidthSizeClass.Expanded &&
        (screenHeight == WindowHeightSizeClass.Medium || screenHeight == WindowHeightSizeClass.Expanded)
    ) {

        // For expanded screens (large tablets or desktop), setup navigation for tablets
        SetupNavForTablet(smartphoneDisplay = false)

    } else {

        // For compact and medium screens (smartphones or small tablets), setup navigation for smartphones
        SetupNavForSmartphone(smartphoneDisplay = true)

    }

}