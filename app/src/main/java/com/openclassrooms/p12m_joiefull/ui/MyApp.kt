package com.openclassrooms.p12m_joiefull.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.openclassrooms.p12m_joiefull.ui.details.DetailScreen
import com.openclassrooms.p12m_joiefull.ui.details.DetailScreenViewModel
import com.openclassrooms.p12m_joiefull.ui.products.HomeScreen
import com.openclassrooms.p12m_joiefull.ui.products.HomeScreenViewModel
import com.openclassrooms.p12m_joiefull.ui.splash.SplashScreen
import org.koin.androidx.compose.koinViewModel

/**
 * The main entry point for the application's UI, managing the navigation between screens.
 *
 * This Composable defines the navigation flow for the application. It includes:
 * - The Splash Screen as the starting point.
 * - The Products Screen, which is displayed after the splash.
 * - The Details Screen, which displays detailed information based on the selected item.
 *
 * The navigation is managed by [NavController] and each screen's state is handled
 * using ViewModels provided via Koin dependency injection.
 */
@Composable
fun MyApp(windowSizeClass: WindowSizeClass) {

    // Check the window size class to adjust UI for different screen sizes
    when (windowSizeClass.widthSizeClass) {

        // Define UI for compact screens (e.g., mobile phones in portrait mode)
        WindowWidthSizeClass.Compact -> {
            SetupNavForCompactSize()
            }

        // Define UI for medium-sized screens (e.g., tablets in portrait mode)
        WindowWidthSizeClass.Medium -> {
            SetupNavForMediumSize()
        }

        // Define UI for expanded screens (e.g., tablets or landscape mode)
        WindowWidthSizeClass.Expanded -> {
            SetupNavForExpandedSize()
        }

        // Default case
        else -> {
            SetupNavForCompactSize()
        }
    }

}
