package com.openclassrooms.p12m_joiefull.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MainContent() {
    // Structure de l'écran d'accueil
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { innerPadding ->
            Greeting(
                name = "Utilisateur",
                modifier = Modifier.padding(innerPadding)
            )
        }
    )
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Bienvenue, $name!", modifier = modifier)
}
