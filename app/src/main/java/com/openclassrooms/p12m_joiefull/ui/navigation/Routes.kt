package com.openclassrooms.p12m_joiefull.ui.navigation


/**
 * Defines the routes used in the navigation system of the app.
 *
 * This sealed class contains the different destinations in the app, with each route having
 * its corresponding string representation. The `Details` route accepts a parameter for the item ID.
 */
sealed class Routes(val route: String) {

    /**
     * Represents the Splash Screen route.
     * This is the starting screen of the app, typically showing a loading or introductory screen.
     */
    data object Splash : Routes("splash")

    /**
     * Represents the Products Screen route.
     * This screen displays the list of available products or categories.
     */
    data object Products : Routes("products")

    /**
     * Represents the Details Screen route, which includes a dynamic parameter for the item ID.
     * This screen shows detailed information for a selected item.
     *
     * @property ARGUMENT The name of the argument that carries the item ID for this route.
     */
    data object Details : Routes("details/{itemId}") {
        const val ARGUMENT = "itemId" // The key for the item ID argument in the route
    }

}