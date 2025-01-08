package com.openclassrooms.p12m_joiefull.data.model

/**
 * Represents a category containing a collection of related items.
 *
 * This class is used to group items that share common attributes, purposes, or themes.
 * Each category is identified by a unique name and contains a list of associated items.
 *
 * @property name The name of the category, used as an identifier or label.
 * @property items A list of [Item] objects that belong to this category.
 * Each item represents a specific product or entity grouped under this category.
 */
data class Category(
    val name: String,
    val items: List<Item>
)