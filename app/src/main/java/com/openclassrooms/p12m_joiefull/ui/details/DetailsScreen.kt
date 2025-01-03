package com.openclassrooms.p12m_joiefull.ui.details

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.openclassrooms.p12m_joiefull.R
import com.openclassrooms.p12m_joiefull.data.model.Item
import com.openclassrooms.p12m_joiefull.ui.theme.LocalExtendedColors

@Composable
fun DetailsScreen(
    navController: NavController,
    item: Item,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        PictureBox(
            navController = navController,
            url = item.picture.url,
            description = item.picture.description,
            likes = item.likes
        )
        Spacer(modifier = Modifier.height(25.dp))
        DescriptionColumn(
            name = item.name,
            rating = item.rating,
            price = item.price,
            originalPrice = item.originalPrice,
            description = item.picture.description
        )
        Spacer(modifier = Modifier.height(16.dp))
        UserInput(url = "https://xsgames.co/randomusers/assets/avatars/male/0.jpg")
    }
}

@Composable
private fun PictureBox(
    navController: NavController,
    url: String,
    description: String,
    likes: Int
) {
    val isFavorite = rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .clip(MaterialTheme.shapes.large) // Coins arrondis
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
        Icon(
            imageVector = Icons.Default.Share,
            contentDescription = null,
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
//                .clickable { shareContent(context, "Check out this awesome content: https://example.com") }
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
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

@Composable
private fun DescriptionColumn(
    name: String,
    rating: Float,
    price: Double,
    originalPrice: Double,
    description: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        InformationBox(name, rating, price, originalPrice)

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
            text = description,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
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
            .fillMaxWidth()
            .height(40.dp) // Taille ajustée pour ressembler au design
            .background(MaterialTheme.colorScheme.surface)
    ) {

        // nom de l'article
        Text(
            modifier = Modifier
                .align(Alignment.TopStart),
            text = name,
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onSurface
        )

        // Groupe icône + evaluation en bas à droite
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd),
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
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        // prix de l'article
        Text(
            modifier = Modifier
                .align(Alignment.BottomStart),
            text = price.toString() + "€",
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
            color = MaterialTheme.colorScheme.onSurface
        )

        // ancien prix de l'article
        Text(
            modifier = Modifier
                .align(Alignment.BottomEnd),
            text = originalPrice.toString() + "€",
            style = MaterialTheme.typography.bodySmall.copy(
                textDecoration = TextDecoration.LineThrough, // Applique le barré
                fontSize = 14.sp
            ),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

@Composable
private fun UserInput(url: String) {

    var userRating by remember { mutableIntStateOf(0) } // Variable mutable pour le rating
    var userComment by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = url,
                contentDescription = "user's avatar",
                modifier = Modifier
                    .padding(end = 6.dp)
                    .size(40.dp)
                    .clip(CircleShape), // Applique les coins arrondis

                contentScale = ContentScale.Crop, // Remplit la box en coupant si nécessaire
                placeholder = painterResource(id = R.drawable.ic_launcher_background)
            )
            StarRating(
                rating = userRating,
                onRatingChanged = { newRating -> userRating = newRating })
        }
        OutlinedTextField(
            value = userComment,
            onValueChange = {
                userComment = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            placeholder = {
                Text(
                    text = "Partagez ici vos impressions sur cette pièce", // Texte d'indication
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f), // Couleur grise pour le placeholder
                        fontSize = 14.sp
                    ),
                )
            },
            shape = RoundedCornerShape(15.dp)
        )
    }
}

@Composable
private fun StarRating(
    rating: Int,
    onRatingChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Affiche les 5 étoiles
        for (i in 1..5) {
            IconButton(
                onClick = { onRatingChanged(i) } // Définit la nouvelle note lorsque l'étoile est cliquée
            ) {
                Icon(
                    imageVector = if (i <= rating) Icons.Outlined.Star else Icons.Default.Star,
                    contentDescription = "Rate $i stars",
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier.size(24.dp) // Taille des étoiles
                )
            }
        }
    }
}

@Composable
private fun ShareContent(context: Context, content: String) {

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain" // Type MIME pour le texte
        putExtra(Intent.EXTRA_TEXT, content) // Contenu à partager
    }

    // Vérifiez si une application peut gérer l'intent
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(Intent.createChooser(intent, "Share via"))
    }
}

// PREVIEWS

//// Classe qui simule le comportement de CategoriesViewModel
//class FakeCategoriesViewModel : CategoriesViewModel() {
//
//    // Utiliser MutableStateFlow pour simuler un ViewModel réel
//    private val _item = MutableStateFlow<Item?>(null)
//    override val item: StateFlow<Item?> = _item
//
//    private val _errorMessage = MutableStateFlow<String?>(null)
//    override val errorMessage: StateFlow<String?> = _errorMessage
//
//    // Simuler une mise à jour de l'item
//    fun loadItem() {
//        _item.value = Item(
//            id = 1,
//            name = "Pull torsadé",
//            rating = 4.6f,
//            price = 69.99,
//            originalPrice = 95.00,
//            likes = 120,
//            picture = Picture(
//                url = "https://example.com/pull-torsade.jpg",
//                description = "Pull vert forêt à motif torsadé élégant."
//            )
//        )
//    }
//
//    // Simuler un message d'erreur
//    fun setErrorMessage(message: String) {
//        _errorMessage.value = message
//    }
//}

//@Preview(showBackground = true)
//@Composable
//private fun PreviewDetailsScreen() {
//    val item = Item(
//        id = 1,
//        picture = Picture(
//            url = "https://xsgames.co/randomusers/assets/avatars/male/0.jpg", // URL de l'image
//            description = "Pull vert forêt à motif torsadé élégant, tricot finement travaillé avec manches bouffantes et col montant; doux et chalereux.", // Description du produit
//        ),
//        name = "Pull torsadé", // Nom du produit
//        likes = 24, // Nombre de likes
//        rating = 4.6f, // Évaluation du produit
//        price = 69.99, // Prix du produit
//        originalPrice = 95.00, // Ancien prix du produit
//    )
//    DetailsScreen(itemId = item.id)
//}

@Preview
@Composable
private fun PreviewPictureBox() {
    val navController = rememberNavController()

    PictureBox(
        navController = navController,
        url = "https://xsgames.co/randomusers/assets/avatars/male/0.jpg",
        description = "Picture of a person",
        likes = 24
    )
}

@Preview
@Composable
private fun PreviewDescriptionColumn() {
    // Exemple de données factices pour la preview
    DescriptionColumn(
        name = "Pull torsadé",
        rating = 4.6f,
        price = 69.99,
        originalPrice = 95.00,
        description = "Pull vert forêt à motif torsadé élégant, tricot finement travaillé avec manches bouffantes et col montant; doux et chalereux."
    )
}

@Preview
@Composable
private fun PreviewInformationBox() {
    InformationBox(
        name = "Pull torsadé",
        rating = 4.6f,
        price = 69.99,
        originalPrice = 95.00
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewUserInput() {
    UserInput(url = "https://example.com/avatar.jpg")
}

@Preview
@Composable
private fun PreviewStarRating() {
    var rating by remember { mutableIntStateOf(0) } // État local pour la note
    Box(
        modifier = Modifier.background(Color.White) // Applique un fond blanc
    ) {
        StarRating(
            rating = 0,
            onRatingChanged = { newRating -> rating = newRating }
        )
    }
}
