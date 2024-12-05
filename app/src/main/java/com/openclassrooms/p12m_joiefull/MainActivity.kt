package com.openclassrooms.p12m_joiefull

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.openclassrooms.p12m_joiefull.ui.theme.JoiefullTheme

/**
 * Main activity for the Joiefull app.
 *
 * This activity sets the content view using Jetpack Compose and applies edge-to-edge support
 * for immersive layouts. It also sets the theme for the app and displays a simple UI with a title.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Enable edge-to-edge support for a full-screen layout
        enableEdgeToEdge()
        setContent {
            // Apply the app's custom theme defined in ui/theme
            JoiefullTheme {
                // The layout and components of the app
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    content = { innerPadding ->
                        // Display the title text, with padding from Scaffold
                        Title(modifier = Modifier.padding(innerPadding))
                    }
                )
            }
        }
    }
}

/**
 * Composable function to display the title text.
 *
 * @param modifier The modifier to be applied to the text. It can be used to add padding or other layout behaviors.
 */
@Composable
fun Title(modifier: Modifier = Modifier) {
    // Display a text element with the app's headline style
    Text(
        text = "Bienvenue dans l'application",
        style = MaterialTheme.typography.headlineLarge,
        modifier = modifier
    )
}