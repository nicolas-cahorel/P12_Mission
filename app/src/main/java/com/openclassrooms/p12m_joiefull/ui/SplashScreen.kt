package com.openclassrooms.p12m_joiefull.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.openclassrooms.p12m_joiefull.R
import com.openclassrooms.p12m_joiefull.ui.theme.LocalExtendedColors

@Preview(showBackground = true)
@Composable
fun SplashScreen() {

    val extendedColors = LocalExtendedColors.current

    Surface(

        modifier = Modifier
            .fillMaxSize()
            .background(
            color = extendedColors.orange
        )

    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center // Centre l'image dans le Box
        ) {

            Image(
                painter = painterResource(id = R.drawable.joiefull_logo),
                contentDescription = "Logo de l'application"
            )
        }
    }
}
