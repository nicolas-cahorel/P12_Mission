package com.openclassrooms.p12m_joiefull.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.openclassrooms.p12m_joiefull.R
import com.openclassrooms.p12m_joiefull.ui.theme.JoiefullTheme

//@Preview
//@Composable
//fun ItemsDisplay() {
//    JoiefullTheme {
//        // Liste des catégories définie directement dans la fonction
//        val categories = listOf(
//            Category("Tops"),
//            Category("Bottoms"),
//            Category("Shoes"),
//            Category("Accessories")
//        )
//
//        // État sélectionné
//        val selected: MutableState<Int?> = remember { mutableStateOf(null) }
//
//        // Utilisation de Column pour afficher la liste des catégories
//        Column() {
//            categories.forEach { category ->
//                Text(
//                    text = item.category,
//                    modifier = Modifier
//                        .padding(8.dp),
//                )
//
//                // Ligne des éléments de la categorie
//                categorie.items.forEach { item ->
//                    Row(modifier = Modifier.fillMaxWidth()) {
//                        Text(text = item, modifier = Modifier.padding(end = 8.dp))
//                    }
//                }
//            }
//        }
//    }
//}


@Preview
@Composable
fun ItemRowAccessories() {
    Text(
        text = stringResource(R.string.category_accessories),
        fontSize = 18.sp,
        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
    )
}