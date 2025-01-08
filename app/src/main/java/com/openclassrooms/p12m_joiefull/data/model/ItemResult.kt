package com.openclassrooms.p12m_joiefull.data.model

/**
 * A sealed class representing the result of fetching an individual item.
 *
 * This class is used to model the outcomes of an operation to retrieve an item, encapsulating both
 * successful retrieval and potential error states. It allows for clean handling of different scenarios
 * in a type-safe manner.
 */
sealed class ItemResult {

    /**
     * Represents a successful result containing a single item.
     *
     * This state indicates that the requested item was successfully retrieved from the data source
     * (e.g., an API or database).
     *
     * @property item The [Item] object that contains the details of the successfully retrieved item.
     */
    data class Success(val item: Item) : ItemResult()

    /**
     * Represents an error state encountered during the operation to fetch the item.
     *
     * This state indicates that the operation failed, providing an error message to help diagnose
     * or communicate the issue to the user or developer.
     *
     * @property message A string message describing the error, useful for debugging or user feedback.
     */
    data class Error(val message: String) : ItemResult()

}