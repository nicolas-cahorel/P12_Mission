package com.openclassrooms.p12m_joiefull.ui.detailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.p12m_joiefull.data.model.ItemResult
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
 * ViewModel responsible for managing the loading and processing of item data.
 *
 * This ViewModel interacts with the [ItemsRepository] to retrieve item data from an API and
 * emits the results or error messages to the UI through [StateFlow] properties.
 *
 * @property itemsRepository The repository providing access to the item data.
 * @property dispatcher The coroutine dispatcher for executing background tasks (default is [Dispatchers.IO]).
 */
class DetailScreenViewModel(
    itemId: Int,
    private val itemsRepository: ItemsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    // StateFlow for emitting the state of the details screen, including loading, success, and error.
    private var _detailScreenState =
        MutableStateFlow<DetailScreenState>(DetailScreenState.Loading)
    val detailScreenState: StateFlow<DetailScreenState> get() = _detailScreenState

    init {
        loadItem(itemId)
    }

    /**
     * Fetches the item details from the repository and updates the UI state.
     *
     * This function runs within a coroutine. It processes the response from the repository
     * and updates the state of the screen based on the result. It handles specific exceptions
     * like [IOException] and [UnknownHostException] and updates the UI accordingly.
     *
     * @param itemId The ID of the item to be fetched from the repository.
     */
    private fun loadItem(itemId: Int) {
        viewModelScope.launch(dispatcher) {

            // Collect the flow from the repository and handle the result
            when (val itemResult = itemsRepository.fetchItem(itemId).first()) {

                // In case of success, update the UI state to show the item details
                is ItemResult.Success -> _detailScreenState.value =
                    DetailScreenState.DisplayDetail(itemResult.item)

                // In case of an error, update the UI state to show the error message
                is ItemResult.Error -> _detailScreenState.value =
                    DetailScreenState.Error(itemResult.message)

            }
        }
    }
}