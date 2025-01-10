package com.openclassrooms.p12m_joiefull.ui.homeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.openclassrooms.p12m_joiefull.R
import com.openclassrooms.p12m_joiefull.data.model.Category
import com.openclassrooms.p12m_joiefull.data.model.Item
import com.openclassrooms.p12m_joiefull.ui.theme.LocalExtendedColors

/**
 * Main composable function that displays the Home screen.
 *
 * This screen shows the list of product categories and their associated items.
 * It handles loading, error, and display states through the [HomeScreenState].
 *
 * @param navController The [NavController] used for navigation between screens.
 * @param state The state of the [HomeScreenState], determining the UI to display.
 */
@Composable
fun HomeScreen(
    navController: NavController,
    state: HomeScreenState
) {

    val extendedColors = LocalExtendedColors.current

    when (state) {

        // Display loading indicator
        is HomeScreenState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = extendedColors.orange)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.joiefull_logo),
                    contentDescription = "Logo de l'application",
                    modifier = Modifier.align(Alignment.Center)
                )
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 120.dp)
                )
            }
        }

        // Display error message
        is HomeScreenState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Display the list of categories and related items
        is HomeScreenState.DisplayItems -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center,

            ) {
                CategoriesColumn(
                    categories = state.categories,
                    navController = navController
                )
            }
        }
    }
}

/**
 * Composable function that displays a list of categories and their associated products.
 *
 * @param categories The list of categories to be displayed.
 * @param navController The [NavController] for navigating to product details.
 */
@Composable
private fun CategoriesColumn(
    categories: List<Category>,
    navController: NavController
) {
    LazyColumn(modifier = Modifier.padding(0.dp)) {
        items(categories) { category ->
            Text(
                text = category.name,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
            )
            ProductsRow(
                navController = navController,
                items = category.items
            )

        }
    }
}

/**
 * Composable function that displays a horizontal list of products within a category.
 *
 * @param navController The [NavController] for navigating to product details.
 * @param items The list of items to be displayed.
 */
@Composable
private fun ProductsRow(
    navController: NavController,
    items: List<Item>
) {
    LazyRow(modifier = Modifier.padding(horizontal = 8.dp)) {
        items(items) { item ->
            ProductColumn(
                navController = navController,
                item = item,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}

/**
 * Composable function that displays a single product with its image and information.
 *
 * @param navController The [NavController] for navigating to the product details screen.
 * @param item The [Item] representing the product to be displayed.
 * @param modifier The [Modifier] used to decorate the composable UI.
 */
@Composable
private fun ProductColumn(
    navController: NavController,
    item: Item,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .clickable {
                navController.navigate("DetailScreen/${item.id}")
            }
    ) {

        // Show product image and likes
        PictureBox(
            url = item.url,
            description = item.description,
            likes = item.likes
        )

        // Show product name, rating, price, and original price
        InformationBox(
            name = item.name,
            rating = item.rating,
            price = item.price,
            originalPrice = item.originalPrice
        )
    }
}

/**
 * Composable function that displays the product image and the like button with the number of likes.
 *
 * @param url The URL of the product image.
 * @param description The description of the product image for accessibility.
 * @param likes The number of likes for the product.
 */
@Composable
private fun PictureBox(
    url: String,
    description: String,
    likes: Int
) {

    val isFavorite = rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .size(180.dp) // Taille ajustée pour ressembler au design
            .clip(MaterialTheme.shapes.medium) // Coins arrondis
            .background(MaterialTheme.colorScheme.surface)
    ) {

        // Display the product image
        AsyncImage(
            model = url,
            contentDescription = description,
            modifier = Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.medium), // Applique les coins arrondis
            contentScale = ContentScale.Crop, // Remplit la box en coupant si nécessaire
            placeholder = painterResource(id = R.drawable.ic_launcher_background)
        )

        // Display the like button and number of likes in the bottom-right corner
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
                imageVector = if (isFavorite.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = if (isFavorite.value) "Remove from favorites" else "Add to favorites",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .size(16.dp)
                    .padding(end = 4.dp)
                    .clickable { isFavorite.value = !isFavorite.value }
            )
            Text(
                text = likes.toString(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

/**
 * Composable function that displays the product information, including name, rating, price, and original price.
 *
 * @param name The name of the product.
 * @param rating The product's rating.
 * @param price The current price of the product.
 * @param originalPrice The original price of the product.
 */
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
            .height(40.dp) // Adjust size to match the design
            .background(MaterialTheme.colorScheme.surface)
    ) {

        // Display the product name
        Text(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(2.dp)
                .widthIn(max = 140.dp),
            text = name,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1, // Permet un nombre illimité de lignes
            overflow = TextOverflow.Ellipsis // Si le texte dépasse, il sera tronqué
        )

        // Display the product's rating
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

        // Display the current price
        Text(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(2.dp),
            text = price.toString() + "€",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface
        )

        // Display the original price
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

//PREVIEWS

@Preview
@Composable
private fun PreviewHomeScreen() {

    val fakeNavController = rememberNavController()
    val fakeState = HomeScreenState.DisplayItems(
        listOf(
            Category(
                "Hauts", listOf(
                    Item(1, "url", "Veste urbaine", "Veste urbaine", 24, 4.3f, 89.00, 120.00, "haut"),
                    Item(2, "url", "Pull torsadé", "Pull torsadé", 56, 4.6f, 69.00, 95.00, "haut")
                )
            ),

            Category(
                "Bas", listOf(
                    Item(3, "url", "Jean slim", "Jean slim", 40, 4.3f, 49.00, 65.00, "bas"),
                    Item(4, "url", "Pantalon large", "Pantalon large", 38, 4.2f, 54.00, 70.00, "bas")
                )
            ),

            Category(
                "Sacs", listOf(
                    Item(5, "url", "Sac à dos", "Sac à dos", 10, 2.5f, 12.00, 24.00, "accessoire"),
                    Item(6, "url", "Sac à main", "Sac à main", 20, 3.5f, 18.00, 36.00, "accessoire")
                )
            )
        )
    )

    HomeScreen(
        navController = fakeNavController,
        state = fakeState
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewProductsRow() {
    val fakeNavController = rememberNavController()
    val sampleProducts = listOf(
        Item(
            id = 1,
            url = "https://xsgames.co/randomusers/assets/avatars/male/1.jpg",
            description = "Product 1 Image",
            name = "Product 1",
            likes = 120,
            rating = 4.5f,
            price = 19.99,
            originalPrice = 24.99,
            category = "category1"
        ),
        Item(
            id = 2,
            url = "https://xsgames.co/randomusers/assets/avatars/female/2.jpg",
            description = "Product 2 Image",
            name = "Product 2",
            likes = 75,
            rating = 3.8f,
            price = 14.99,
            originalPrice = 19.99,
            category = "category1"
        ),
        Item(
            id = 3,
            url = "https://xsgames.co/randomusers/assets/avatars/male/3.jpg",
            description = "Product 3 Image",
            name = "Product 3",
            likes = 200,
            rating = 4.9f,
            price = 29.99,
            originalPrice = 34.99,
            category = "category1"
        )
    )
    ProductsRow(
        items = sampleProducts,
        navController = fakeNavController
    )
}

@Preview
@Composable
private fun PreviewProductColumn() {
    val fakeNavController = rememberNavController() // NavController factice pour la Preview
    val fakeItem = Item(
        id = 1,
        url = "https://xsgames.co/randomusers/assets/avatars/male/0.jpg",
        description = "Picture of a person",
        likes = 24,
        name = "Gym Nastyk",
        rating = 3.5f,
        price = 19.99,
        originalPrice = 29.99,
        category = "category1"
    )

    ProductColumn(
        navController = fakeNavController,
        item = fakeItem
    )
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