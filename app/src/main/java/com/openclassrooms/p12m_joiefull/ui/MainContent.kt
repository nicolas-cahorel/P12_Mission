package com.openclassrooms.p12m_joiefull.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.koin.androidx.compose.koinViewModel


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

@Composable
fun LazyColumn() {
    Colomn(){
        titre()
        lazyrow()
    }

//TODO: display items sorted by category
}

@Preview
@Composable
fun Title() {
    val categoriesViewModel: CategoriesViewModel = koinViewModel()
    val categoriesList = categoriesViewModel.categories.collectAsState(initial = emptyList())
    val categoryToDisplayIndex = 0

    Text(
        text = if (categoriesList.value.isNotEmpty() && categoryToDisplayIndex < categoriesList.value.size) {
            categoriesList.value[categoryToDisplayIndex]
        } else {
            "No category available"
        }
    )
}

