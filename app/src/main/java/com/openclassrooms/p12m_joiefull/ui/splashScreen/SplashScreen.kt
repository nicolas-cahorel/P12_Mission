package com.openclassrooms.p12m_joiefull.ui.splashScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.openclassrooms.p12m_joiefull.R
import com.openclassrooms.p12m_joiefull.ui.navigation.Routes
import com.openclassrooms.p12m_joiefull.ui.theme.LocalExtendedColors
import kotlinx.coroutines.delay

/**
 * Composable function representing the splash screen of the application.
 *
 * The splash screen displays the logo of the application for a brief period before navigating
 * to the products screen. The navigation is done using [NavController].
 *
 * @param navController The [NavController] used for navigation after the splash screen.
 */
@Composable
fun SplashScreen(navController: NavController) {

    val extendedColors = LocalExtendedColors.current

    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate(Routes.HomeScreen.route) {
            popUpTo(Routes.SplashScreen.route) { inclusive = true }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = extendedColors.orange
            ),
        contentAlignment = Alignment.Center
    ) {

        // Display the application logo.
        Image(
            painter = painterResource(id = R.drawable.joiefull_logo),
            contentDescription = "Logo de l'application"
        )
    }
}

// PREVIEW

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = LocalExtendedColors.current.orange
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.joiefull_logo),
            contentDescription = "Logo de l'application"
        )
    }
}