package com.openclassrooms.p12m_joiefull.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.openclassrooms.p12m_joiefull.R
import com.openclassrooms.p12m_joiefull.data.model.Category
import com.openclassrooms.p12m_joiefull.data.model.Item
import com.openclassrooms.p12m_joiefull.data.model.Picture
import com.openclassrooms.p12m_joiefull.ui.theme.LocalExtendedColors


//@Composable
//fun MainColumn(
//    categories: List<Category>,
//    modifier: Modifier = Modifier
//) {
//    LazyColumn(
//        modifier = modifier.fillMaxSize(),
//        contentPadding = PaddingValues(16.dp),
//        verticalArrangement = Arrangement.spacedBy(12.dp) // Espace entre les catégories
//    ) {
//        categories.forEach { category ->
//            item {
//                // Titre de la catégorie
//                Text(
//                    text = category.name,
//                    style = MaterialTheme.typography.titleLarge,
//                    modifier = Modifier.padding(vertical = 8.dp)
//                )
//            }
//            items(category.items) { item ->
//                // Affichage des articles de la catégorie
//                ProductRow(List<Category>())
//            }
//        }
//    }
//}

@Composable
fun ProductColumn(
    productImageUrl: String,
    productImageDescription: String,
    productLikesCount: Int,
    productName: String,
    productRating: Float,
    productPrice: Double,
    productOriginalPrice: Double,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.background)
    ) {
        PictureBox(
            url = productImageUrl,
            description = productImageDescription,
            likes = productLikesCount
        )

        InformationBox(
            name = productName,
            rating = productRating,
            price = productPrice,
            originalPrice = productOriginalPrice
        )
    }
}
@Preview
@Composable
fun PreviewProductColumn() {
    ProductColumn(
        productImageUrl = "https://xsgames.co/randomusers/assets/avatars/male/0.jpg",
        productImageDescription = "Picture of a person",
        productLikesCount = 24,
        productName = "Gym Nastyk",
        productRating = 3.5f,
        productPrice = 19.99,
        productOriginalPrice = 29.99
    )
}

@Composable
fun PictureBox(
    url: String,
    description: String,
    likes: Int
) {
    Box(
        modifier = Modifier
            .size(180.dp) // Taille ajustée pour ressembler au design
            .clip(MaterialTheme.shapes.medium) // Coins arrondis
            .background(MaterialTheme.colorScheme.surface)
    ) {

        // Image occupant toute la box
        AsyncImage(
            model = url,
            contentDescription = description,
            modifier = Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.medium), // Applique les coins arrondis
            contentScale = ContentScale.Crop, // Remplit la box en coupant si nécessaire
            placeholder = painterResource(id = R.drawable.ic_launcher_background)
        )

        // Groupe icône + likes en bas à droite
        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                    shape = MaterialTheme.shapes.medium
                )
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp)) // Espace entre l'icône et le texte
            Text(
                text = likes.toString(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview
@Composable
fun PreviewPictureBox() {
    PictureBox(
        url = "https://xsgames.co/randomusers/assets/avatars/male/0.jpg",
        description = "Picture of a person",
        likes = 24
    )
}


@Composable
fun InformationBox(
    name: String,
    rating: Float,
    price: Double,
    originalPrice: Double
) {

    val extendedColors = LocalExtendedColors.current

    Box(
        modifier = Modifier
            .width(180.dp)
            .height(40.dp) // Taille ajustée pour ressembler au design
            .background(MaterialTheme.colorScheme.surface)
    ) {

        // nom de l'article
        Text(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(2.dp),
            text = name,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onSurface
        )

        // Groupe icône + evaluation en bas à droite
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "rating icon",
                tint = extendedColors.orange,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(2.dp)) // Espace entre l'icône et le texte
            Text(
                text = rating.toString(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        // prix de l'article
        Text(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(2.dp),
            text = price.toString() + "€",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface
        )

        // ancien prix de l'article
        Text(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(2.dp),
            text = originalPrice.toString()+ "€",
            style = MaterialTheme.typography.bodySmall.copy(
                textDecoration = TextDecoration.LineThrough // Applique le barré
            ),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

@Preview
@Composable
fun PreviewInformationBox() {
    InformationBox(
        name = "Gym Nastyk",
        rating = 3.5f,
        price = 19.99,
        originalPrice = 29.99
    )
}


@Composable
fun PreviewMainColumn() {
    val sampleCategories = listOf(
        Category(
            name = "Male",
            items = listOf(
                Item(
                    1,
                    picture = Picture(
                        url = "https://xsgames.co/randomusers/assets/avatars/male/0.jpg",
                        description = "random male avatar"
                    ),
                    name = "Mister 1",
                    likes = 10,
                    price = 1.0,
                    originalPrice = 1.2
                ),
                Item(
                    1,
                    picture = Picture(
                        url = "https://xsgames.co/randomusers/assets/avatars/male/1.jpg",
                        description = "random male avatar 2"
                    ),
                    name = "Mister 2",
                    likes = 20,
                    price = 2.0,
                    originalPrice = 2.4
                )
            )
        ),
        Category(
            name = "Female",
            items = listOf(
                Item(
                    3,
                    picture = Picture(
                        url = "https://xsgames.co/randomusers/assets/avatars/female/0.jpg",
                        description = "random female avatar"
                    ),
                    name = "Miss 1",
                    likes = 10,
                    price = 1.0,
                    originalPrice = 1.2
                ),
                Item(
                    4,
                    picture = Picture(
                        url = "https://xsgames.co/randomusers/assets/avatars/female/1.jpg",
                        description = "random female avatar 2"
                    ),
                    name = "Miss 2",
                    likes = 20,
                    price = 2.0,
                    originalPrice = 2.4
                )
            )
        )
    )
//    MainColumn(categories = sampleCategories)
}