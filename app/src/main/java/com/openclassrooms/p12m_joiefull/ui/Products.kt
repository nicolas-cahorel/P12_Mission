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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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

@Composable
fun CategoriesScreen(
    viewModel: CategoriesViewModel,
    modifier: Modifier = Modifier
) {
    val categoriesFlow = viewModel.categories.collectAsState(initial = emptyList())
    val errorMessageFlow = viewModel.errorMessage.collectAsState(initial = null)

    Box(modifier = modifier.fillMaxSize()) {
        when {
            categoriesFlow.value.isNotEmpty() -> {
                CategoriesColumn(categories = categoriesFlow.value)
            }

            errorMessageFlow.value != null -> {
                Text(
                    text = errorMessageFlow.value!!,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else -> {
                Text(
                    text = "Loading...",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCategoriesScreen() {
    // Simuler des données pour le ViewModel
    val categories = listOf(
        Category(
            "Hauts", listOf(
                Item(1, Picture("url", "Veste urbaine"), "Veste urbaine", 24, 4.3f, 89.00, 120.00),
                Item(2, Picture("url", "Pull torsadé"), "Pull torsadé", 56, 4.6f, 69.00, 95.00)
            )
        ),

        Category(
            "Bas", listOf(
                Item(3, Picture("url", "Jean slim"), "Jean slim", 40, 4.3f, 49.00, 65.00),
                Item(4, Picture("url", "Pantalon large"), "Pantalon large", 38, 4.2f, 54.00, 70.00)
            )
        ),

        Category(
            "Sacs", listOf(
                Item(5, Picture("url", "Sac à dos"), "Sac à dos", 10, 2.5f, 12.00, 24.00),
                Item(6, Picture("url", "Sac à main"), "Sac à main", 20, 3.5f, 18.00, 36.00)
            )
        )
    )


    val errorMessage: String? = null // Simule l'absence d'erreur

    // Créer un composant CategoriesScreen avec des données simulées
    CategoriesScreenPreview(categories = categories, errorMessage = errorMessage)
}

@Composable
private fun CategoriesScreenPreview(categories: List<Category>, errorMessage: String?) {
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            categories.isNotEmpty() -> {
                CategoriesColumn(categories = categories)
            }

            errorMessage != null -> {
                Text(
                    text = errorMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else -> {
                Text(
                    text = "Loading...",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}


@Composable
private fun CategoriesColumn(
    categories: List<Category>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.padding(0.dp)) {
        items(categories) { category ->
            Text(
                text = category.name,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
            )
            ProductsRow(products = category.items)

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCategoriesColumn() {
    val sampleCategories = listOf(
        Category(
            name = "Electronics",
            items = listOf(
                Item(
                    id = 1,
                    picture = Picture(
                        url = "https://xsgames.co/randomusers/assets/avatars/male/1.jpg",
                        description = "Smartphone Image"
                    ),
                    name = "Smartphone",
                    likes = 100,
                    rating = 4.5f,
                    price = 299.99,
                    originalPrice = 349.99
                ),
                Item(
                    id = 2,
                    picture = Picture(
                        url = "https://xsgames.co/randomusers/assets/avatars/female/2.jpg",
                        description = "Laptop Image"
                    ),
                    name = "Laptop",
                    likes = 150,
                    rating = 4.7f,
                    price = 999.99,
                    originalPrice = 1199.99
                )
            )
        ),
        Category(
            name = "Books",
            items = listOf(
                Item(
                    id = 3,
                    picture = Picture(
                        url = "https://xsgames.co/randomusers/assets/avatars/male/3.jpg",
                        description = "Book Cover"
                    ),
                    name = "Science Fiction Book",
                    likes = 80,
                    rating = 4.2f,
                    price = 19.99,
                    originalPrice = 24.99
                )
            )
        )
    )

    CategoriesColumn(categories = sampleCategories)
}


@Composable
private fun ProductsRow(
    products: List<Item>,
    modifier: Modifier = Modifier
) {
    LazyRow(modifier = modifier.padding(horizontal = 8.dp)) {
        items(products) { product ->
            ProductColumn(
                imageUrl = product.picture.url,
                imageDescription = product.picture.description,
                likesCount = product.likes,
                name = product.name,
                rating = product.rating,
                price = product.price,
                originalPrice = product.originalPrice,
                modifier = Modifier.padding(end = 8.dp)
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewProductsRow() {
    val sampleProducts = listOf(
        Item(
            id = 1,
            picture = Picture(
                url = "https://xsgames.co/randomusers/assets/avatars/male/1.jpg",
                description = "Product 1 Image"
            ),
            name = "Product 1",
            likes = 120,
            rating = 4.5f,
            price = 19.99,
            originalPrice = 24.99
        ),
        Item(
            id = 2,
            picture = Picture(
                url = "https://xsgames.co/randomusers/assets/avatars/female/2.jpg",
                description = "Product 2 Image"
            ),
            name = "Product 2",
            likes = 75,
            rating = 3.8f,
            price = 14.99,
            originalPrice = 19.99
        ),
        Item(
            id = 3,
            picture = Picture(
                url = "https://xsgames.co/randomusers/assets/avatars/male/3.jpg",
                description = "Product 3 Image"
            ),
            name = "Product 3",
            likes = 200,
            rating = 4.9f,
            price = 29.99,
            originalPrice = 34.99
        )
    )

    ProductsRow(products = sampleProducts)
}


@Composable
private fun ProductColumn(
    imageUrl: String,
    imageDescription: String,
    likesCount: Int,
    name: String,
    rating: Float,
    price: Double,
    originalPrice: Double,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.background)
    ) {
        PictureBox(
            url = imageUrl,
            description = imageDescription,
            likes = likesCount
        )

        InformationBox(
            name = name,
            rating = rating,
            price = price,
            originalPrice = originalPrice
        )
    }
}

@Preview
@Composable
private fun PreviewProductColumn() {
    ProductColumn(
        imageUrl = "https://xsgames.co/randomusers/assets/avatars/male/0.jpg",
        imageDescription = "Picture of a person",
        likesCount = 24,
        name = "Gym Nastyk",
        rating = 3.5f,
        price = 19.99,
        originalPrice = 29.99
    )
}

@Composable
private fun PictureBox(
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
                    .padding(end = 4.dp)
            )
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
private fun PreviewPictureBox() {
    PictureBox(
        url = "https://xsgames.co/randomusers/assets/avatars/male/0.jpg",
        description = "Picture of a person",
        likes = 24
    )
}

@Composable
private fun InformationBox(
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
                modifier = Modifier
                    .size(16.dp)
                    .padding(end = 2.dp)
            )
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
            text = originalPrice.toString() + "€",
            style = MaterialTheme.typography.bodySmall.copy(
                textDecoration = TextDecoration.LineThrough // Applique le barré
            ),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

@Preview
@Composable
private fun PreviewInformationBox() {
    InformationBox(
        name = "Gym Nastyk",
        rating = 3.5f,
        price = 19.99,
        originalPrice = 29.99
    )
}