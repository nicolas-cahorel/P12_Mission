package com.openclassrooms.p12m_joiefull.data.model

/**
 * Represents the result of an API call, including the status code and the list of items.
 *
 * @property apiStatusCode The HTTP status code returned by the API.
 * @property itemsList The list of items returned by the API.
 */
data class ApiResultModel(
    val apiStatusCode: Int,
    val itemsList : List<ItemResultModel>
)

/**
 * Represents the details of a single item.
 *
 * @property id The unique identifier of the item.
 * @property picture The picture information associated with the item.
 * @property name The name of the item.
 * @property category The category to which the item belongs.
 * @property likes The number of likes the item has received.
 * @property price The current price of the item.
 * @property originalPrice The original price of the item before any discounts.
 */
data class ItemResultModel(
    val id: Int,
    val picture: PictureResultModel,
    val name: String,
    val category: String,
    val likes: Int,
    val price: Double,
    val originalPrice: Double
)

/**
 * Represents the picture details of an item.
 *
 * @property url The URL of the picture.
 * @property description The description of the picture.
 */
data class PictureResultModel(
    val url: String,
    val description: String
)