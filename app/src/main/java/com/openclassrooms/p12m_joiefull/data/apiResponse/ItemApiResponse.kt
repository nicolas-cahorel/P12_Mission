package com.openclassrooms.p12m_joiefull.data.apiResponse

import com.openclassrooms.p12m_joiefull.data.model.Category
import com.openclassrooms.p12m_joiefull.data.model.Item
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Represents the details of an item in the API response.
 *
 * This class maps the JSON response for an individual item retrieved from the API.
 *
 * @property id The unique identifier of the item.
 * @property picture The picture details associated with the item, encapsulated in [PictureApiResponse].
 * @property name The name of the item.
 * @property category The category name to which the item belongs.
 * @property likes The number of likes received by the item.
 * @property price The current price of the item.
 * @property originalPrice The original price of the item before any discounts or promotions.
 */
@JsonClass(generateAdapter = true)
data class ItemApiResponse(
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
 * This class maps the JSON response for the picture details associated with an item.
 *
 * @property url The URL pointing to the item's picture.
 * @property description A textual description of the item's picture.
 */
@JsonClass(generateAdapter = true)
data class PictureApiResponse(
    @Json(name = "url") val url: String,
    @Json(name = "description") val description: String
)

/**
 * Extension function to transform a list of [ItemApiResponse] into a list of [Category].
 *
 * Each [Category] object contains a name and the list of items that belong to that category.
 * The items are grouped by their category name.
 *
 * @return A list of [Category] objects, each containing the category name and a list of [Item] objects.
 */
fun List<ItemApiResponse>.categorizedItems(): List<Category> {

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
                    rating = 2.5f,
                    price = items.price,
                    originalPrice = items.originalPrice,
                    category = items.category
                )
            }
        )
    }
}

/**
 * Extension function to transform a list of [ItemApiResponse] into a flat list of [Item].
 *
 * This function maps each [ItemApiResponse] into a corresponding [Item] object without grouping by category.
 *
 * @return A list of [Item] objects, each representing an individual item.
 */
fun List<ItemApiResponse>.toItems(): List<Item> {

    return this.map {
        Item(
            id = it.id,
            url = it.picture.url,
            description = it.picture.description,
            name = it.name,
            likes = it.likes,
            rating = 2.5f,
            price = it.price,
            originalPrice = it.originalPrice,
            category = it.category
        )
    }
}