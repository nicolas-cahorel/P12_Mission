package com.openclassrooms.p12m_joiefull.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.p12m_joiefull.data.model.CategoriesResult
import com.openclassrooms.p12m_joiefull.data.model.Category
import com.openclassrooms.p12m_joiefull.data.repository.CategoriesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.UnknownHostException

/**
 * ViewModel responsible for managing the loading and processing of categories data.
 *
 * This ViewModel interacts with the [CategoriesRepository] to retrieve categories from an API and
 * emits results or error messages to the UI through [SharedFlow] properties.
 *
 * @property categoriesRepository The repository providing access to the categories data.
 * @property dispatcher The coroutine dispatcher for executing background tasks (default is [Dispatchers.IO]).
 */
class CategoriesViewModel(
    private val categoriesRepository: CategoriesRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    companion object {
        private const val KEY_ITEM_IDENTIFIER = "itemIdentifier"
    }

    // Flow for emitting error messages to the UI.
    private var _errorMessage = MutableSharedFlow<String>()
    val errorMessage: SharedFlow<String> get() = _errorMessage.asSharedFlow()

    // Flow for emitting a list of categories to the UI.
    private var _categories = MutableSharedFlow<List<Category>>()
    val categories: SharedFlow<List<Category>> get() = _categories.asSharedFlow()

    init {
        // Automatically load categories when the ViewModel is initialized.
        loadCategories()
    }

    /**
     * Fetches the list of categories from the repository and emits the result.
     *
     * This function runs within a coroutine. It processes the response from the repository
     * and emits either a list of categories or an appropriate error message based on the result.
     * Handles specific exceptions like [IOException] and [UnknownHostException].
     */
    fun loadCategories() {
        viewModelScope.launch(dispatcher) {

            try {
                // Collect the flow from the repository and handle the result
                categoriesRepository.fetchCategories().collect { result ->

                    when (result) {
                        is CategoriesResult.Success -> _categories.emit(result.categories)
                        CategoriesResult.ErrorEmpty -> _errorMessage.emit("No products available.")
                        CategoriesResult.ErrorServer -> _errorMessage.emit("Server error. Please try again later.")
                        CategoriesResult.ErrorUnexpected -> _errorMessage.emit("Unexpected error occurred.")
                        CategoriesResult.ErrorIOException -> _errorMessage.emit("Connection error. Please check your Internet connection.")
                        CategoriesResult.ErrorUnknownHostException -> _errorMessage.emit("No Internet connection. Please check your connection.")
                        CategoriesResult.ErrorException -> _errorMessage.emit("An unknown error occurred. Please try again.")
                    }
                }

            } catch (e: IOException) {
                handleLoadCategoriesError(CategoriesResult.ErrorIOException)
            } catch (e: UnknownHostException) {
                handleLoadCategoriesError(CategoriesResult.ErrorUnknownHostException)
            } catch (e: Exception) {
                handleLoadCategoriesError(CategoriesResult.ErrorException)
            }
        }
    }

    /**
     * Handles errors that occur during the loading of categories.
     *
     * Based on the error type, an appropriate message is emitted to the [errorMessage] flow.
     *
     * @param error The error result to handle, represented as a [CategoriesResult].
     */
    private suspend fun handleLoadCategoriesError(error: CategoriesResult) {
        when (error) {
            CategoriesResult.ErrorIOException -> _errorMessage.emit("Connection error. Please check your Internet connection.")
            CategoriesResult.ErrorUnknownHostException -> _errorMessage.emit("No Internet connection. Please check your connection.")
            CategoriesResult.ErrorException -> _errorMessage.emit("An unknown error occurred. Please try again.")
            else -> _errorMessage.emit("An unspecified error occurred.")
        }
    }
}