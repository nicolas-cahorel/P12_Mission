package com.openclassrooms.p12m_joiefull.data.apiResponse

import com.openclassrooms.p12m_joiefull.data.model.Category
import com.openclassrooms.p12m_joiefull.data.model.Item
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlin.random.Random

/**
 * Represents the details of an item in the API response.
 *
 * @property id The unique identifier of the item.
 * @property picture The picture associated with the item.
 * @property name The name of the item.
 * @property category The category to which the item belongs.
 * @property likes The number of likes the item has received.
 * @property price The current price of the item.
 * @property originalPrice The original price of the item before any discounts.
 */
@JsonClass(generateAdapter = true)
data class ItemsApiResponse(
    @Json(name = "id") val id: Int,
    @Json(name = "picture") val picture: PictureApiResponse,
    @Json(name = "name") val name: String,
    @Json(name = "category") val category: String,
    @Json(name = "likes") val likes: Int,
    @Json(name = "price") val price: Double,
    @Json(name = "original_price") val originalPrice: Double
)

/**
 * Represents the picture details of an item in the API response.
 *
 * @property url The URL of the picture.
 * @property description A textual description of the picture.
 */
@JsonClass(generateAdapter = true)
data class PictureApiResponse(
    @Json(name = "url") val url: String,
    @Json(name = "description") val description: String
)

/**
 * Extension function to transform a list of [ItemsApiResponse] into a list of [Category],
 * where each category contains the grouped items.
 *
 * @return A list of [Category] objects, each containing a name and a list of corresponding [Item] objects.
 */
fun List<ItemsApiResponse>.categorizedItems():List<Category> {

    // Group the items by their category name.
    val categories = this.groupBy { it.category }

    // Transform the grouped data into a list of Category objects.
    return categories.map { (categoryName, itemsInCategory) ->
        Category(
            name = categoryName,
            items = itemsInCategory.map { items ->
                Item(
                    id = items.id,
                    url = items.picture.url,
                    description = items.picture.description,
                    name = items.name,
                    likes = items.likes,
                    rating = (Random.nextDouble(1.0, 5.0).toFloat() * 10).toInt() / 10f,
                    price = items.price,
                    originalPrice = items.originalPrice
                )
            }
        )
    }
}