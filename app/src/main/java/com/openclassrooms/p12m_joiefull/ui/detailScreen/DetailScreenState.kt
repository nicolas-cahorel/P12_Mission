package com.openclassrooms.p12m_joiefull.ui.detailScreen

import com.openclassrooms.p12m_joiefull.data.model.Item

/**
 * Represents the different states of the Details Screen.
 * These states are used to manage and display the UI based on the current state of the data or operations.
 */
sealed interface DetailScreenState {

    /**
     * Represents a loading state during the data retrieval process for the Details Screen.
     * This state is typically used to display a progress bar or loading indicator to the user.
     */
    data object Loading : DetailScreenState

    /**
     * Represents a state where an item is successfully loaded and ready to be displayed on the Details Screen.
     *
     * @property item The [Item] object that contains the details to be displayed on the screen.
     * This object holds all the necessary data that should be shown on the UI once it is loaded successfully.
     */
    data class DisplayDetail(val item: Item) : DetailScreenState

    /**
     * Represents an error state encountered during data loading or processing on the Details Screen.
     * This state provides a message to inform the user about what went wrong.
     *
     * @property message A string message describing the error, which helps users understand the issue.
     * This message can be shown to the user as an alert or in a Snackbar to notify them of the failure.
     */
    data class Error(val message: String) : DetailScreenState

}