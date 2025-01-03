package com.openclassrooms.p12m_joiefull

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.openclassrooms.p12m_joiefull.ui.MyApp
import com.openclassrooms.p12m_joiefull.ui.theme.JoiefullTheme

/**
 * Main activity for the Joiefull app.
 *
 * This activity sets the content view using Jetpack Compose
 * and applies the theme for the app.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JoiefullTheme {
                MyApp()
            }
        }
    }
}