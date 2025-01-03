package com.openclassrooms.p12m_joiefull.ui.products

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.p12m_joiefull.data.model.CategoriesResult
import com.openclassrooms.p12m_joiefull.data.model.Category
import com.openclassrooms.p12m_joiefull.data.model.Item
import com.openclassrooms.p12m_joiefull.data.repository.CategoriesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
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
class ProductsViewModel(
    private val categoriesRepository: CategoriesRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    // StateFlow for emitting error messages to the UI.
    private var _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    // StateFlow for emitting a list of categories to the UI.
    private var _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> get() = _categories

    // Flag to ensure categories are loaded only once.
    private var isCategoriesLoaded = false

    init {
        // Load categories only if they haven't been loaded yet
        if (!isCategoriesLoaded) {
            loadCategories()
            isCategoriesLoaded = true
        }
    }

    /**
     * Fetches the list of categories from the repository and emits the result.
     *
     * This function runs within a coroutine. It processes the response from the repository
     * and emits either a list of categories or an appropriate error message based on the result.
     * Handles specific exceptions like [IOException] and [UnknownHostException].
     */
    private fun loadCategories() {
        viewModelScope.launch(dispatcher) {

            try {
                // Collect the flow from the repository and handle the result
                categoriesRepository.fetchCategories().collect { result ->

                    when (result) {
                        is CategoriesResult.Success -> {
                            _categories.value = result.categories
                        }

                        CategoriesResult.ErrorEmpty -> _errorMessage.value =
                            "No products available."

                        CategoriesResult.ErrorServer -> _errorMessage.value =
                            "Server error. Please try again later."

                        CategoriesResult.ErrorUnexpected -> _errorMessage.value =
                            "Unexpected error occurred."

                        CategoriesResult.ErrorIOException -> _errorMessage.value =
                            "Connection error. Please check your Internet connection."

                        CategoriesResult.ErrorUnknownHostException -> _errorMessage.value =
                            "No Internet connection. Please check your connection."

                        CategoriesResult.ErrorException -> _errorMessage.value =
                            "An unknown error occurred. Please try again."
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

//    fun getItemById(itemId: Int) {
//        val item = _categories.value
//            .flatMap { category -> category.items }
//            .find { item -> item.id == itemId }
//
//        viewModelScope.launch {
//            if (item == null) {
//                _errorMessage.emit("Une erreur est survenue lors du chargement de l'article.")
//
//            } else {
//                _item.value = item
//                Log.d("ViewModel", "Item received: $_item")
//            }
//        }
//    }

    /**
     * Handles errors that occur during the loading of categories.
     *
     * Based on the error type, an appropriate message is emitted to the [errorMessage] flow.
     *
     * @param error The error result to handle, represented as a [CategoriesResult].
     */
    private fun handleLoadCategoriesError(error: CategoriesResult) {
        when (error) {
            CategoriesResult.ErrorIOException -> _errorMessage.value = "Connection error. Please check your Internet connection."
            CategoriesResult.ErrorUnknownHostException -> _errorMessage.value = "No Internet connection. Please check your connection."
            CategoriesResult.ErrorException -> _errorMessage.value = "An unknown error occurred. Please try again."
            else -> _errorMessage.value = "An unspecified error occurred."
        }
    }
}