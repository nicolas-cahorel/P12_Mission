package com.openclassrooms.p12m_joiefull.ui

sealed class Routes(val route: String) {
    data object Splash : Routes("splash")
    data object Products : Routes("products")
    data object Details : Routes("details/{itemId}") {
        const val ARGUMENT = "itemId"
    }
}