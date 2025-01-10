package com.openclassrooms.p12m_joiefull.ui.homeScreen

import com.openclassrooms.p12m_joiefull.data.model.Category

/**
 * Represents the different states of the Details Screen.
 * These states are used to manage and display the UI based on the current state of the data or operations.
 */
sealed interface HomeScreenState {

    /**
     * Represents a loading state during the data retrieval process for the Details Screen.
     * This state is typically used to display a progress bar or loading indicator to the user.
     */
    data object Loading : HomeScreenState

    /**
     * Represents a state where items (categories) are successfully loaded and ready to be displayed on the Home Screen.
     *
     * @property categories A list of [Category] objects containing product categories to be displayed on the screen.
     */
    data class DisplayItems(val categories: List<Category>) : HomeScreenState

    /**
     * Represents an error state encountered during data loading or processing on the Home Screen.
     * This state provides a message to inform the user about what went wrong.
     *
     * @property message A string message describing the error, which helps users understand the issue.
     */
    data class Error(val message: String) : HomeScreenState

}
