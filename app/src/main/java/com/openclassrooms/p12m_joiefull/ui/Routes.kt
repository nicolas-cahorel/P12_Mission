package com.openclassrooms.p12m_joiefull.ui

sealed class Routes(val route: String) {
    object Splash : Routes("splash")
    object Products : Routes("products")
    object Details : Routes("details/{itemId}") {
        const val ARGUMENT = "itemId"
        
        fun createRoute(itemId: Int) = "details/$itemId"
    }
}