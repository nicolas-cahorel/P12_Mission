package com.openclassrooms.p12m_joiefull.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.openclassrooms.p12m_joiefull.data.model.Item
import com.openclassrooms.p12m_joiefull.ui.details.DetailsScreen
import com.openclassrooms.p12m_joiefull.ui.details.DetailsViewModel
import com.openclassrooms.p12m_joiefull.ui.products.ProductsScreen
import com.openclassrooms.p12m_joiefull.ui.products.ProductsViewModel
import com.openclassrooms.p12m_joiefull.ui.splash.SplashScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun MyApp() {
    val navController = rememberNavController()
//    val productsViewModel: ProductsViewModel = koinViewModel()
//    val detailsViewModel: DetailsViewModel = koinViewModel()

//    val categoriesState = productsViewModel.categories.collectAsState(initial = emptyList())
//    val categoriesMessageState = productsViewModel.errorMessage.collectAsState(initial = null)

//    val itemState = detailsViewModel.item.collectAsState(initial = null)

    NavHost(
        navController = navController,
        startDestination = Routes.Splash.route
    ) {
        composable(Routes.Splash.route) {
            SplashScreen(navController)
        }
        composable(Routes.Products.route) {
            val productsViewModel: ProductsViewModel = koinViewModel()
            val categoriesState = productsViewModel.categories.collectAsState(initial = emptyList())
            val categoriesMessageState = productsViewModel.errorMessage.collectAsState(initial = null)
            ProductsScreen(
                navController = navController,
                categories = categoriesState.value,
                errorMessage = categoriesMessageState.value,
                navigateToDetail = {
                    navController.navigate(it)
                }
            )
        }

        composable<Item> { navBackStackEntry ->
            val item: Item = navBackStackEntry.toRoute()
            DetailsScreen(
                navController = navController,
                item = item
            )
        }

//        composable(
//            route = Routes.Details.route) {
//            val item = itemState.value
//            Log.d("Navigation", "Item at navigation: $item")
//            if (item != null) {
//                DetailsScreen(
//                    navController = navController,
//                    item = item
//                )
//            } else {
//                //TODO
//            }
//        }
    }
}

