package com.openclassrooms.p12m_joiefull.ui.navigation


/**
 * Defines the routes used in the navigation system of the app.
 *
 * This sealed class contains the different destinations in the app, with each route having
 * its corresponding string representation. The `DetailScreen` route includes a dynamic parameter
 * for the item ID that can be passed to the screen.
 */
sealed class Routes(val route: String) {

    /**
     * Represents the Splash Screen route.
     *
     * This is the starting screen of the app, typically showing a loading or introductory screen.
     */
    data object SplashScreen : Routes("SplashScreen")

    /**
     * Represents the Home Screen route.
     *
     * This screen displays the list of available products or categories.
     */
    data object HomeScreen : Routes("HomeScreen")

    /**
     * Represents the Detail Screen route, which includes a dynamic parameter for the item ID.
     *
     * This screen shows detailed information for a selected item. The item ID is passed as part
     * of the route, allowing for dynamic navigation based on the selected item.
     *
     * @property ARGUMENT The name of the argument that carries the item ID for this route.
     */
    data object DetailScreen : Routes("DetailScreen/{itemId}") {
        // The key for the item ID argument in the route
        const val ARGUMENT = "itemId" // The key for the item ID argument in the route
    }

}