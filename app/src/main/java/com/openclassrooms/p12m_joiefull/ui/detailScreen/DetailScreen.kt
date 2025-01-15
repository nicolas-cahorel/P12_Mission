package com.openclassrooms.p12m_joiefull.ui.detailScreen

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.openclassrooms.p12m_joiefull.R
import com.openclassrooms.p12m_joiefull.data.model.Item
import com.openclassrooms.p12m_joiefull.ui.theme.LocalExtendedColors

/**
 * Composable function to display the detail screen of an item.
 *
 * @param navController The navigation controller used for navigation.
 * @param state The current state of the screen (Loading, Error, or DisplayDetail).
 */
@Composable
fun DetailScreen(
    smartphoneDisplay: Boolean,
    navController: NavController,
    state: DetailScreenState,
) {

    val extendedColors = LocalExtendedColors.current

    when (state) {

        // Display loading indicator
        is DetailScreenState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = extendedColors.orange)
                    .semantics { contentDescription = "Chargement en cours" }
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
        is DetailScreenState.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Display the details of selected item
        is DetailScreenState.DisplayDetail -> {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                PictureBox(
                    smartphoneDisplay = smartphoneDisplay,
                    navController = navController,
                    item = state.item
                )
                Spacer(modifier = Modifier.height(25.dp))
                DescriptionColumn(
                    item = state.item
                )
                Spacer(modifier = Modifier.height(16.dp))
                UserInput(url = "https://xsgames.co/randomusers/assets/avatars/male/0.jpg")
            }
        }
    }
}

/**
 * Displays the image and interaction options (favorite, share, back).
 *
 * @param navController The navigation controller for handling back navigation.
 * @param item The item whose details are being displayed.
 */
@Composable
private fun PictureBox(
    smartphoneDisplay: Boolean,
    navController: NavController,
    item: Item
) {
    val isFavorite = rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth()
            .height(500.dp)
            .clip(MaterialTheme.shapes.large)
    ) {

        // Image display with rounded corners
        AsyncImage(
            model = item.url,
            contentDescription = item.description,
            modifier = Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.medium)
                .semantics { contentDescription = "Image de l'article : ${item.description}" },
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.ic_launcher_background)
        )

        // Favorite icon and likes counter
        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                    shape = MaterialTheme.shapes.medium
                )
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .clickable {
                    isFavorite.value = !isFavorite.value
                    val message = if (isFavorite.value) {
                        "Article ajouté aux favoris"
                    } else {
                        "Article retiré des favoris"
                    }
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (isFavorite.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = if (isFavorite.value) "Retirer des favoris" else "Ajouter aux favoris",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .size(16.dp)
                    .padding(end = 4.dp)

            )
            Text(
                text = item.likes.toString(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        // Share icon
        Icon(
            imageVector = Icons.Default.Share,
            contentDescription = "Partager cet article",
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                    shape = CircleShape
                )
                .size(24.dp)
                .padding(3.dp)
                .clickable {
                    val shareText = "Cet article pourrait vous interresser: ${item.name}\n" +
                            "Description: ${item.description}\n" +
                            "Prix: ${item.price}€\n" +
                            "Ancien Price: ${item.originalPrice}€\n" +
                            "Photo de l'article: ${item.url}"
                    shareContent(context, shareText)
                }
        )

        if (smartphoneDisplay) {
            // Back button icon
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Retour à l'écran principal",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                        shape = CircleShape
                    )
                    .size(24.dp)
                    .padding(3.dp)
                    .clickable { navController.popBackStack() }
            )
        }
    }
}

/**
 * Displays the item's description and additional details (name, price, original price, etc.).
 *
 * @param item The item whose details are being displayed.
 */
@Composable
private fun DescriptionColumn(
    item: Item
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        InformationBox(item)

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
                .semantics {
                    contentDescription = "Descritpion de l'article : ${item.description}"
                },
            text = item.description,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

/**
 * Displays a box containing the item's name, rating, price, and original price.
 *
 * @param item The item whose details are being displayed.
 */
@Composable
private fun InformationBox(
    item: Item
) {

    val extendedColors = LocalExtendedColors.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(MaterialTheme.colorScheme.surface)
    ) {

        // Item name
        Text(
            modifier = Modifier
                .align(Alignment.TopStart)
                .widthIn(max = 200.dp)
                .semantics { contentDescription = "Nom de l'article : ${item.name}" },
            text = item.name,
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        // Rating display
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = extendedColors.orange,
                modifier = Modifier
                    .size(16.dp)
                    .padding(end = 2.dp)
            )
            Text(
                text = item.rating.toString(),
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.semantics {
                    contentDescription = "Evaluation de l'article : ${item.rating} étoiles"
                }
            )
        }

        // Price
        Text(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .semantics {
                    contentDescription =
                        "Prix de l'article : ${item.price.toInt()} euros ${"%.0f".format((item.price % 1) * 100)}"
                },
            text = item.price.toString() + "€",
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
            color = MaterialTheme.colorScheme.onSurface
        )

        // Original price
        Text(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .semantics {
                    contentDescription =
                        "Ancien prix de l'article : ${item.originalPrice.toInt()} euros ${
                            "%.0f".format((item.originalPrice % 1) * 100)
                        }"
                },
            text = item.originalPrice.toString() + "€",
            style = MaterialTheme.typography.bodySmall.copy(
                textDecoration = TextDecoration.LineThrough,
                fontSize = 14.sp
            ),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

/**
 * Displays a user input section for commenting and rating the item.
 *
 * @param url The URL of the user's avatar.
 */
@Composable
private fun UserInput(url: String) {

    var userComment by rememberSaveable { mutableStateOf("") }
    val ratingState = rememberSaveable { mutableIntStateOf(0) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {

        // Avatar and rating bar
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = url,
                contentDescription = "Avatar de l'utilisateur",
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.surface)
                    .padding(end = 8.dp)
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.ic_launcher_background)
            )

            UserRatingBar(
                ratingState = ratingState,
                size = 40.dp,
            )
        }

        // User's comment input
        OutlinedTextField(
            value = userComment,
            onValueChange = { userComment = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .background(color = MaterialTheme.colorScheme.surface)
                .semantics { contentDescription = "Champ de saisie pour votre avis sur l'article" },
            placeholder = {
                Text(
                    text = "Partagez ici vos impressions sur cette pièce",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 14.sp
                    ),
                )
            },
            shape = RoundedCornerShape(15.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(color = MaterialTheme.colorScheme.surface)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Ajoutez votre avis",
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier
                    .align(Alignment.CenterEnd)

                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = CircleShape
                    )
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        shape = CircleShape
                    )
                    .size(36.dp)
                    .clickable {
                        if (ratingState.intValue > 0 && userComment.isNotEmpty()) {
                            ratingState.intValue = 0
                            userComment = ""
                            Toast.makeText(context, "Votre avis a été ajouté", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(
                                context,
                                "Veuillez saisir une note et un commentaire",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            )
        }

    }
}

/**
 * Displays a star rating bar for user feedback.
 *
 * @param size The size of each star.
 * @param ratingState The state representing the user's rating.
 */
@Composable
private fun UserRatingBar(
    size: Dp = 64.dp,
    ratingState: MutableState<Int> = remember { mutableIntStateOf(0) },
) {

    Row(
        modifier = Modifier
            .wrapContentSize()
            .background(color = MaterialTheme.colorScheme.surface),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // 2. Star Icon Generation Loop
        for (value in 1..5) {
            StarIcon(
                size = size,
                ratingValue = value,
                ratingState = ratingState
            )
        }
    }
}

/**
 * A composable function that displays a star icon for rating purposes.
 *
 * @param size The size of the star icon.
 * @param ratingState The current rating state (keeps track of the user's rating).
 * @param ratingValue The specific rating value for this star (1-5).
 */
@Composable
fun StarIcon(
    size: Dp,
    ratingState: MutableState<Int>,
    ratingValue: Int
) {
    val extendedColors = LocalExtendedColors.current

    // Color animation based on the current rating
    val tint by animateColorAsState(
        targetValue = if (ratingValue <= ratingState.value) extendedColors.orange else Color.Gray,
        label = ""
    )

    // Determine which icon to use based on the rating value
    val icon =
        if (ratingValue <= ratingState.value) R.drawable.star_icon else R.drawable.outlined_star_icon

    Icon(
        painter = painterResource(icon),
        tint = tint,
        contentDescription = "Attribuez une note à l'article entre 1 et 5 étoiles",
        modifier = Modifier
            .size(size)
            .clickable {
                ratingState.value = ratingValue
            }
            .semantics { contentDescription = "Attribuez la note de $ratingValue étoiles" }
    )
}

/**
 * Share the content using Android's share intent.
 *
 * @param context The context used for invoking the share action.
 * @param content The text to be shared.
 */
private fun shareContent(context: Context, content: String) {

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, content)
    }

    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(Intent.createChooser(intent, "Share via"))
    }
}

// PREVIEWS

@Preview(showBackground = true)
@Composable
private fun PreviewDetailScreen() {
    val navController = rememberNavController()
    val fakeState = DetailScreenState.DisplayDetail(
        Item(
            id = 1,
            url = "https://xsgames.co/randomusers/assets/avatars/male/0.jpg",
            description = "Pull vert forêt à motif torsadé élégant, tricot finement travaillé avec manches bouffantes et col montant; doux et chalereux.",
            name = "Pull torsadé",
            likes = 24,
            rating = 4.6f,
            price = 69.99,
            originalPrice = 95.00,
            category = "haut"
        )
    )

    DetailScreen(
        smartphoneDisplay = true,
        navController = navController,
        state = fakeState
    )
}

@Preview
@Composable
private fun PreviewPictureBox() {
    val navController = rememberNavController()
    val fakeItem = Item(
        id = 1,
        url = "https://xsgames.co/randomusers/assets/avatars/male/0.jpg",
        description = "Pull vert forêt à motif torsadé élégant, tricot finement travaillé avec manches bouffantes et col montant; doux et chalereux.",
        name = "Pull torsadé",
        likes = 24,
        rating = 4.6f,
        price = 69.99,
        originalPrice = 95.00,
        category = "haut"
    )

    PictureBox(
        smartphoneDisplay = true,
        navController = navController,
        item = fakeItem
    )
}

@Preview
@Composable
private fun PreviewDescriptionColumn() {
    val fakeItem = Item(
        id = 1,
        url = "https://xsgames.co/randomusers/assets/avatars/male/0.jpg",
        description = "Pull vert forêt à motif torsadé élégant, tricot finement travaillé avec manches bouffantes et col montant; doux et chalereux.",
        name = "Pull torsadé",
        likes = 24,
        rating = 4.6f,
        price = 69.99,
        originalPrice = 95.00,
        category = "haut"
    )

    DescriptionColumn(
        item = fakeItem
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewUserInput() {
    UserInput(url = "https://example.com/avatar.jpg")
}