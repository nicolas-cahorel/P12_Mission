package com.openclassrooms.p12m_joiefull.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.p12m_joiefull.data.model.CategoriesResult
import com.openclassrooms.p12m_joiefull.data.repository.ItemsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.UnknownHostException

/**
 * ViewModel responsible for managing the loading and processing of categories data.
 *
 * This ViewModel interacts with the [ItemsRepository] to retrieve categories from an API and
 * emits results or error messages to the UI through [StateFlow] properties.
 *
 * @property itemsRepository The repository providing access to the categories data.
 * @property dispatcher The coroutine dispatcher for executing background tasks (default is [Dispatchers.IO]).
 */
class HomeScreenViewModel(
    private val itemsRepository: ItemsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    // StateFlow for emitting the current state of the Home Screen (loading, displaying items, or error).
    private var _homeScreenState = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val homeScreenState: StateFlow<HomeScreenState> get() = _homeScreenState

    /**
     * Initializes the ViewModel and triggers the loading of categories.
     * This function is called when the ViewModel is created.
     */
    init {
        loadCategories()
    }

    /**
     * Fetches the list of categories from the repository and emits the result.
     *
     * This function runs within a coroutine and processes the response from the repository.
     * It emits either a list of categories or an error message based on the result.
     * Handles specific exceptions like [IOException] and [UnknownHostException].
     */
    private fun loadCategories() {
        viewModelScope.launch(dispatcher) {

            // Collect the flow from the repository and handle the result
            when (val categoriesResult = itemsRepository.fetchCategories().first()) {

                // If the categories are successfully fetched, update the state with the list of categories.
                is CategoriesResult.Success -> _homeScreenState.value =
                    HomeScreenState.DisplayItems(categoriesResult.categories)

                // If an error occurs, update the state with the error message.
                is CategoriesResult.Error -> _homeScreenState.value =
                    HomeScreenState.Error(categoriesResult.message)

            }
        }
    }
}