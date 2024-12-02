package com.openclassrooms.p12m_joiefull.data.model

data class ApiResultModel(
    val apiStatusCode: Int,
    val itemInformation : ItemsResultModel
)

data class ItemsResultModel(
    val id: Int,
    val picture: PicturesResultModel,
    val name: String,
    val category: String,
    val likes: Int,
    val price: Double,
    val originalPrice: Double
)

data class PicturesResultModel(
    val url: String,
    val description: String
)