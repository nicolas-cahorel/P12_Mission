package com.openclassrooms.p12m_joiefull.ui.navigation

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.openclassrooms.p12m_joiefull.ui.details.DetailScreen
import com.openclassrooms.p12m_joiefull.ui.details.DetailScreenViewModel
import com.openclassrooms.p12m_joiefull.ui.products.HomeScreen
import com.openclassrooms.p12m_joiefull.ui.products.HomeScreenViewModel
import com.openclassrooms.p12m_joiefull.ui.splash.SplashScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun SetupNavForSmartphone(widthSizeClass: WindowWidthSizeClass) {

    // Initialize the NavController to manage the navigation between screens.
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Splash.route
    ) {

        // Splash Screen: Displays the splash logo and navigates to the Products Screen after a delay.
        composable(Routes.Splash.route) {
            SplashScreen(navController)
        }

        // Home Screen: Displays a list of products. The state is fetched from the ViewModel.
        composable(Routes.Products.route) {
            // Retrieve the HomeScreenViewModel using Koin for dependency injection.
            val homeScreenViewModel: HomeScreenViewModel = koinViewModel()
            // Collect the state of the HomeScreen and observe it.
            val homeScreenState = homeScreenViewModel.homeScreenState.collectAsState().value
            // Pass the navigation controller and state to the ProductsScreen composable.
            HomeScreen(
                navController = navController,
                state = homeScreenState
            )
        }

        // Details Screen: Displays detailed information about a specific item.
        composable(route = Routes.Details.route) { backStackEntry ->

            // Retrieve the item ID from the navigation arguments.
            val itemId = backStackEntry.arguments?.getString(Routes.Details.ARGUMENT)?.toIntOrNull()

            // Retrieve the DetailScreenViewModel using Koin for dependency injection.
            val detailScreenViewModel: DetailScreenViewModel = koinViewModel { parametersOf(itemId) }

            // Collect the state of the DetailScreen and observe it.
            val detailScreenState = detailScreenViewModel.detailScreenState.collectAsState().value

            // Pass the navigation controller and state to the DetailsScreen composable.
            DetailScreen(
                widthSizeClass = widthSizeClass,
                navController = navController,
                state = detailScreenState
            )
        }
    }
}