package com.openclassrooms.p12m_joiefull.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val categoriesViewModel: CategoriesViewModel = koinViewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.Splash.route
    ) {
        composable(Routes.Splash.route) {
            SplashScreen(navController)
        }
        composable(Routes.Products.route) {
            val categories = categoriesViewModel.categories.collectAsState(initial = emptyList()).value
            val errorMessage = categoriesViewModel.errorMessage.collectAsState(initial = null).value
            ProductsScreen(navController, categories, errorMessage)
        }
        composable(Routes.Details.route) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString(Routes.Details.ARGUMENT)?.toIntOrNull()

            val item = categoriesViewModel.categories.collectAsState(initial = emptyList()).value
                .flatMap { it.items }
                .find { it.id == itemId }

            if (item != null) {
                DetailsScreen(item)
            }
        }
    }
}