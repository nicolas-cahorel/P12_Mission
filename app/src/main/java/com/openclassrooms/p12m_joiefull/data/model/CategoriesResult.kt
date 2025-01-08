package com.openclassrooms.p12m_joiefull.data.model

/**
 * A sealed class representing the result of fetching categories.
 *
 * This class encapsulates the possible outcomes when attempting to fetch a list of categories.
 * It provides a way to handle both successful results and error scenarios in a type-safe manner.
 */
sealed class CategoriesResult {

    /**
     * Represents a successful result containing a list of categories.
     *
     * @property categories A non-empty list of [Category], where each category contains a name and a list of [Item].
     */
    data class Success(val categories: List<Category>) : CategoriesResult()

    /**
     * Represents an error that occurred while attempting to fetch categories.
     *
     * @property message A descriptive message explaining the nature of the error.
     */
    data class Error(val message: String) : CategoriesResult()

}