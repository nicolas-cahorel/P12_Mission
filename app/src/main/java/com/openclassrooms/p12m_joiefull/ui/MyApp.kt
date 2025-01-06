package com.openclassrooms.p12m_joiefull.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.openclassrooms.p12m_joiefull.ui.details.DetailsScreen
import com.openclassrooms.p12m_joiefull.ui.products.ProductsScreen
import com.openclassrooms.p12m_joiefull.ui.products.ProductsViewModel
import com.openclassrooms.p12m_joiefull.ui.splash.SplashScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Splash.route
    ) {

        // Splash Screen
        composable(Routes.Splash.route) {
            SplashScreen(navController)
        }

        // Products Screen
        composable(Routes.Products.route) {
            val productsViewModel: ProductsViewModel = koinViewModel()
            val categoriesState = productsViewModel.categories.collectAsState(initial = emptyList())
            val categoriesMessageState = productsViewModel.errorMessage.collectAsState(initial = null)

            ProductsScreen(
                navController = navController,
                categories = categoriesState.value,
                errorMessage = categoriesMessageState.value
            )
        }

        // Details Screen
        composable(route = Routes.Details.route) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString(Routes.Details.ARGUMENT)?.toIntOrNull()
            val productsViewModel: ProductsViewModel = koinViewModel()
            val item = productsViewModel.categories.collectAsState(initial = emptyList()).value
                .flatMap { it.items }
                .find { it.id == itemId }

                if (item != null) {
                    DetailsScreen(
                        navController = navController,
                        item = item
                    )
                    Log.e("Navigation", "Item : $item")
            } else {
                Log.e("Navigation", "Item is null")
            }
        }
    }
}

