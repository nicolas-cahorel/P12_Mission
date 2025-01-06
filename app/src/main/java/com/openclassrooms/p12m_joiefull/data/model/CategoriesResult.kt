package com.openclassrooms.p12m_joiefull.data.model

/**
 * A sealed class representing the result of fetching categories.
 *
 * This class encapsulates the possible outcomes when attempting to fetch a list of categories,
 * including success and various types of errors.
 */
sealed class CategoriesResult {

    /**
     * Represents a successful result containing a list of categories.
     *
     * @property categories A list of categories, each containing a list of items.
     */
    data class Success(val categories : List<Category>): CategoriesResult()

    /**
     * Represents an error indicating that the categories list is empty.
     */
    data object ErrorEmpty: CategoriesResult()

    /**
     * Represents an error caused by a server-related issue.
     */
    data object ErrorServer: CategoriesResult()

    /**
     * Represents an error result for unexpected errors or unknown issues.
     */
    data object ErrorUnexpected: CategoriesResult()

    /**
     * Represents an error caused by an input/output exception, such as a network disruption.
     */
    data object ErrorIOException: CategoriesResult()

    /**
     * Represents an error caused by a failure to resolve the host, typically due to lack of Internet connection.
     */
    data object ErrorUnknownHostException: CategoriesResult()

    /**
     * Represents a generic exception that does not fall into other error categories.
     */
    data object ErrorException: CategoriesResult()
}

/**
 * Represents a category containing a collection of related items.
 *
 * A category groups items with similar attributes or purposes.
 *
 * @property name The name of the category.
 * @property items The list of items belonging to this category.
 */
data class Category(
    val name: String,
    val items: List<Item>
)

/**
 * Represents the details of an individual item.
 *
 * An item includes various attributes such as pricing and popularity metrics.
 *
 * @property id The unique identifier of the item.
 * @property picture The picture details associated with the item.
 * @property name The name or title of the item.
 * @property likes The number of likes or endorsements the item has received.
 * @property float TODO
 * @property price The current price of the item.
 * @property originalPrice The original price of the item before any discounts.
 */
data class Item(
    val id: Int,
    val url: String,
    val description: String,
    val name: String,
    val likes: Int,
    val rating: Float,
    val price: Double,
    val originalPrice: Double
)