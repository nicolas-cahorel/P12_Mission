package com.openclassrooms.p12m_joiefull

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.openclassrooms.p12m_joiefull.ui.MyApp
import com.openclassrooms.p12m_joiefull.ui.theme.JoiefullTheme

/**
 * Main activity for the Joiefull app.
 *
 * This activity is the entry point of the app. It uses Jetpack Compose to set the UI and applies
 * the app's theme using the [JoiefullTheme] composable. The UI is defined by the [MyApp] composable.
 */
class MainActivity : ComponentActivity() {
    /**
     * Called when the activity is created.
     * Sets the content view of the activity using Jetpack Compose.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state, if any.
     */

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setting the content view of the activity using Compose
        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)
            // Applying the app's theme to the content
            JoiefullTheme {
                // Setting up the main composable of the app
                MyApp(windowSizeClass)
            }
        }
    }
}