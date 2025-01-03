package com.openclassrooms.p12m_joiefull.data.model

/**
 * A sealed class representing the result of fetching categories.
 *
 * This class encapsulates the possible outcomes when attempting to fetch a list of categories,
 * including success and various types of errors.
 */
sealed class ItemResult {

    /**
     * Represents a successful result containing a list of categories.
     *
     * @property categories A list of categories, each containing a list of items.
     */
    data class Success(val item: Item): ItemResult()

    /**
     * Represents an error indicating that the categories list is empty.
     */
    data object Loading: ItemResult()

}