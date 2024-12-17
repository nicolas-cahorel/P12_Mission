package com.openclassrooms.p12m_joiefull.ui

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
import com.openclassrooms.p12m_joiefull.ui.theme.LocalExtendedColors
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(1000)
        navController.navigate(Routes.Products.route) {
            popUpTo(Routes.Splash.route) { inclusive = true }
        }
    }
    val extendedColors = LocalExtendedColors.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = extendedColors.orange
            ),
        contentAlignment = Alignment.Center // Centre l'image dans le Box
    ) {

        Image(
            painter = painterResource(id = R.drawable.joiefull_logo),
            contentDescription = "Logo de l'application"
        )
    }
}

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
