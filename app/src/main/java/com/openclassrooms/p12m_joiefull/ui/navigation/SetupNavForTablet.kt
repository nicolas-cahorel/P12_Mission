package com.openclassrooms.p12m_joiefull.ui.navigation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.openclassrooms.p12m_joiefull.ui.detailScreen.DetailScreen
import com.openclassrooms.p12m_joiefull.ui.detailScreen.DetailScreenViewModel
import com.openclassrooms.p12m_joiefull.ui.homeScreen.HomeScreen
import com.openclassrooms.p12m_joiefull.ui.homeScreen.HomeScreenViewModel
import com.openclassrooms.p12m_joiefull.ui.splashScreen.SplashScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

/**
 * Sets up the navigation for the tablet layout, handling the different screens
 * like SplashScreen, HomeScreen, and DetailScreen, with navigation and state management.
 * This layout uses a side-by-side view for HomeScreen and DetailScreen.
 *
 * @param smartphoneDisplay Boolean that indicates whether the smartphone display mode
 * is enabled or not, affecting how the DetailScreen is shown.
 */
@Composable
fun SetupNavForTablet(smartphoneDisplay: Boolean) {

    // Initialize the NavController to manage the navigation between screens.
    val navController = rememberNavController()

    // Variable to track recompositions for the HomeScreen, using remembered mutable state.
    val homeScreenRecomposingCount = remember { mutableIntStateOf(0) }

    // Variable to track recompositions for the DetailScreen, using remembered mutable state.
    val detailScreenRecomposingCount = remember { mutableIntStateOf(0) }

    // Setting up the navigation host to manage composable screens.
    NavHost(
        navController = navController,
        startDestination = Routes.SplashScreen.route
    ) {

        /**
         * Splash Screen: Displays the splash logo and navigates to the Home Screen
         * after a delay.
         */
        composable(Routes.SplashScreen.route) {
            SplashScreen(navController)
        }

        /**
         * Home Screen: Displays a list of items (e.g., products). The state is collected
         * from the HomeScreenViewModel and displayed.
         *
         * This layout uses a side-by-side view with HomeScreen on the left and DetailScreen
         * on the right, in a `Row`.
         */
        composable(Routes.HomeScreen.route) {

            // Retrieve the HomeScreenViewModel using Koin for dependency injection.
            val homeScreenViewModel: HomeScreenViewModel = koinViewModel()

            // Collect the state of the HomeScreen and observe it.
            val homeScreenState = homeScreenViewModel.homeScreenState.collectAsState().value

            // Retrieve the DetailScreenViewModel using Koin for dependency injection.
            val detailScreenViewModel: DetailScreenViewModel = koinViewModel { parametersOf(0) }

            // Collect the state of the DetailScreen and observe it.
            val detailScreenState =
                detailScreenViewModel.detailScreenState.collectAsState().value

            // Increment the recomposition counter for HomeScreen and log it each time a recomposition occurs.
            SideEffect {
                homeScreenRecomposingCount.intValue++
                Log.d(
                    "SetupNavForSmartphone",
                    "(re)composition of HomeScreen: ${homeScreenRecomposingCount.intValue}"
                )
            }

            // Layout the HomeScreen and DetailScreen side by side in a Row.
            Row(modifier = Modifier.fillMaxHeight()) {

                Box(modifier = Modifier.weight(3f)) {

                    // Pass the navigation controller and state to the HomeScreen composable.
                    HomeScreen(
                        navController = navController,
                        state = homeScreenState
                    )
                }

                Box(modifier = Modifier.weight(2f)) {

                    // Pass the navigation controller and state to the DetailsScreen composable.
                    DetailScreen(
                        smartphoneDisplay = smartphoneDisplay,
                        navController = navController,
                        state = detailScreenState
                    )
                }
            }
        }

        /**
         * Detail Screen: Displays detailed information about a specific item.
         * The item ID is extracted from the navigation arguments.
         * This layout uses a side-by-side view with HomeScreen on the left and DetailScreen
         * on the right.
         */
        composable(route = Routes.DetailScreen.route) { backStackEntry ->

            // Retrieve the item ID from the navigation arguments.
            val itemId =
                backStackEntry.arguments?.getString(Routes.DetailScreen.ARGUMENT)?.toIntOrNull()

            // Retrieve the DetailScreenViewModel using Koin for dependency injection, passing the item ID.
            val detailScreenViewModel: DetailScreenViewModel =
                koinViewModel { parametersOf(itemId) }

            // Collect the state of the DetailScreen and observe it.
            val detailScreenState = detailScreenViewModel.detailScreenState.collectAsState().value

            // Increment the recomposition counter for DetailScreen and log it each time a recomposition occurs.
            SideEffect {
                detailScreenRecomposingCount.intValue++
                Log.d(
                    "SetupNavForSmartphone",
                    "(re)composition of DetailScreen: ${detailScreenRecomposingCount.intValue}"
                )
            }

            // Layout the HomeScreen and DetailScreen side by side in a Row.
            Row(modifier = Modifier.fillMaxHeight()) {

                Box(modifier = Modifier.weight(3f)) {

                    // Retrieve the HomeScreenViewModel using Koin for dependency injection.
                    val homeScreenViewModel: HomeScreenViewModel = koinViewModel()

                    // Collect the state of the HomeScreen and observe it.
                    val homeScreenState = homeScreenViewModel.homeScreenState.collectAsState().value

                    // Pass the navigation controller and state to the HomeScreen composable.
                    HomeScreen(
                        navController = navController,
                        state = homeScreenState
                    )
                }

                Box(modifier = Modifier.weight(2f)) {
                    // Pass the navigation controller and state to the DetailsScreen composable.
                    DetailScreen(
                        smartphoneDisplay = smartphoneDisplay,
                        navController = navController,
                        state = detailScreenState
                    )
                }
            }
        }
    }
}