package com.openclassrooms.p12m_joiefull.data.apiResponse

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Represents the response containing a list of items fetched from the API.
 *
 * @property items The list of items returned by the API.
 */
@JsonClass(generateAdapter = true)
data class ItemsList(
    @Json(name = "items") val items: List<ItemsApiResponse>
)

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
    @Json(name = "picture") val picture: Picture,
    @Json(name = "name") val name: String,
    @Json(name = "category") val category: String,
    @Json(name = "likes") val likes: Int,
    @Json(name = "price") val price: Double,
    @Json(name = "original_price") val originalPrice: Double
)

/**
 * Represents the picture details of an item.
 *
 * @property url The URL of the picture.
 * @property description The description of the picture.
 */
@JsonClass(generateAdapter = true)
data class Picture(
    @Json(name = "url") val url: String,
    @Json(name = "description") val description: String
)