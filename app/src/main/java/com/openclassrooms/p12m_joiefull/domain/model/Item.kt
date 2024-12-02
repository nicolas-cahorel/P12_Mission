package com.openclassrooms.p12m_joiefull.domain.model


data class Item(
    var id: Long,
    var pictureUrl: String,
    var description : String,
    var name: String,
    var category: String,
    var likes: Int,
    var rating : Float,
    var price: Int,
    var originalPrice: Int
)
