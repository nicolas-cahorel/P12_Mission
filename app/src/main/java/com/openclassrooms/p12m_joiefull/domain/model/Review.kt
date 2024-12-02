package com.openclassrooms.p12m_joiefull.domain.model

data class Review(
    var id: Long,
    var itemId: Long,
    var author : String,
    var rate: Int,
    var content: String
)
