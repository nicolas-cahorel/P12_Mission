package com.openclassrooms.p12m_joiefull.data.apiResponse

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

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

@JsonClass(generateAdapter = true)
data class Picture(
    @Json(name = "url") val url: String,
    @Json(name = "description") val description: String
)