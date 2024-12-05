package com.openclassrooms.p12m_joiefull.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.p12m_joiefull.data.model.ItemResultModel
import com.openclassrooms.p12m_joiefull.data.repository.ItemsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.UnknownHostException

/**
 * ViewModel for managing the loading and handling of item data.
 *
 * This ViewModel interacts with the [ItemsRepository] to fetch item data from the API and handle the
 * API response, emitting the results and status messages to the UI.
 */
class ItemsViewModel(
    private val itemsRepository: ItemsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    companion object {
        private const val KEY_ITEM_IDENTIFIER = "itemIdentifier"
    }

    // Flow to emit messages to the UI (e.g., success or error messages)
    private var _itemsViewModelMessage = MutableSharedFlow<String>()
    val itemsViewModelMessage: SharedFlow<String> get() = _itemsViewModelMessage.asSharedFlow()

    // Flow to emit a list of items to the UI for display
    private var _itemsList = MutableSharedFlow<List<ItemResultModel>>()
    val itemsList: SharedFlow<List<ItemResultModel>> get() = _itemsList.asSharedFlow()

    init {
        // Load items when the ViewModel is initialized
        loadItems()
    }

    /**
     * Fetches the list of items from the [ItemsRepository] and emits the result.
     *
     * This method uses a coroutine to fetch the data and handles different types of errors, such as network errors,
     * by emitting appropriate messages to the UI.
     */
    fun loadItems() {
        viewModelScope.launch(dispatcher) {

            try {
                // Collect the flow from the ItemsRepository to fetch item data
                itemsRepository.fetchItemsData().collect { itemInformationResult ->

                    // Extract the API status code and item list from the response
                    val statusCode = itemInformationResult.apiStatusCode
                    val itemsList = itemInformationResult.itemsList


                    // Handle the API status code and update the item list or show an error message
                    handleApiStatusCode(
                        statusCode,
                        itemsList
                    )
                }

            } catch (e: IOException) {
                // Handle network errors (e.g., no Internet connection)
                _itemsViewModelMessage.emit("Connection error. Please check your Internet connection.")
            } catch (e: UnknownHostException) {
                // Handle specific errors related to the host not being reachable
                _itemsViewModelMessage.emit("No Internet connection. Please check your connection.")
            } catch (e: Exception) {
                // Handle any other types of unexpected errors
                println("Exception: ${e.message}")
                _itemsViewModelMessage.emit("An error occurred. Please try again.")
            }
        }
    }

    /**
     * Handles the API response status code and updates the UI accordingly.
     *
     * Depending on the status code, the appropriate message is emitted to the UI, and the item list is updated
     * if the response was successful.
     *
     * @param statusCode The HTTP status code returned by the API response.
     * @param itemsList The list of items fetched from the API.
     */
    private suspend fun handleApiStatusCode(
        statusCode: Int,
        itemsList: List<ItemResultModel>
    ) {
        val message: String

        when (statusCode) {
            200 -> {
                // Successful API response with items
                if (itemsList != emptyList<ItemResultModel>()) {
                    _itemsList.emit(itemsList)
                    message = "Items successfully loaded"
                } else {
                    // API returned no items
                    message = "Error: items not found."
                }
            }

            // Error handling for various status codes
            0 -> message = "Error 0: API has not returned HTTP status code"
            1 -> message = "Error 1: no response from API"
            2 -> message = "Error 2: unexpected error"
            3 -> message = "Error 3: no internet access"
            in 3..99 -> message = "Error $statusCode: Unknown Error"
            in 100..199 -> message = "Error $statusCode: Information Error"
            in 201..299 -> message = "Error $statusCode: Success Error"
            in 300..399 -> message = "Error $statusCode: Redirection Error"
            in 400..499 -> message = "Error $statusCode: Client Error"
            in 500..599 -> message = "Error $statusCode: Server Error"
            in 600..999 -> message = "Error $statusCode: Unknown Error"
            else -> message = "Unexpected Error. Please try again."
        }
        // Emit the status message to the UI
        _itemsViewModelMessage.emit(message)
    }

}