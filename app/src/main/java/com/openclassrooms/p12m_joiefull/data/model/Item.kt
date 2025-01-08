package com.openclassrooms.p12m_joiefull.data.model

/**
 * Represents the details of an individual item.
 *
 * This class is used to model a specific item with attributes that describe its
 * visual representation, characteristics, and pricing details. Each item can be
 * associated with a category for organizational purposes.
 *
 * @property id The unique identifier of the item, used for database or API operations.
 * @property url The URL of the item's image or associated resource, used to display visuals in the UI.
 * @property description A brief textual description of the item, providing additional context or details.
 * @property name The name or title of the item, used as its primary label.
 * @property likes The number of user endorsements or "likes" the item has received, indicating popularity.
 * @property rating The average rating of the item, typically on a scale from 0.0 to 5.0, reflecting user feedback.
 * @property price The current price of the item, reflecting discounts or promotions if applicable.
 * @property originalPrice The original price of the item before any discounts, used to calculate savings.
 * @property category The name of the category to which this item belongs, used for classification or filtering.
 */
data class Item(
    val id: Int,
    val url: String,
    val description: String,
    val name: String,
    val likes: Int,
    val rating: Float,
    val price: Double,
    val originalPrice: Double,
    val category: String
)