package com.openclassrooms.p12m_joiefull.data.repository

import com.openclassrooms.p12m_joiefull.data.apiResponse.categorizedItems
import com.openclassrooms.p12m_joiefull.data.apiResponse.toItems
import com.openclassrooms.p12m_joiefull.data.model.CategoriesResult
import com.openclassrooms.p12m_joiefull.data.model.ItemResult
import com.openclassrooms.p12m_joiefull.data.network.ItemsClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Repository for fetching categorized items and individual items from the API.
 *
 * This repository handles communication with the [ItemsClient] to retrieve data from the API,
 * processes the responses, and emits the appropriate results through Kotlin Flows.
 *
 * @property dataService The [ItemsClient] instance used to perform API requests.
 */
class ItemsRepository(private val dataService: ItemsClient) {

    /**
     * Fetches the list of categories from the API.
     *
     * This function emits a [Flow] of [CategoriesResult], representing the result of the operation:
     * - [CategoriesResult.Success] if the API call succeeds and returns valid data.
     * - [CategoriesResult.Error] if an error occurs during the API call or data processing.
     *
     * @return A [Flow] emitting [CategoriesResult], containing either the list of categories or an error message.
     */
    fun fetchCategories(): Flow<CategoriesResult> = flow {

//        try {
        val apiResponse = dataService.getItems()

        when (apiResponse.code()) {
            200 -> {
                val items = apiResponse.body()
                if (!items.isNullOrEmpty()) {
                    // Emit success with categorized items
                    emit(CategoriesResult.Success(items.categorizedItems()))
                } else {
                    // Emit error if the response body is empty
                    emit(CategoriesResult.Error("No products available."))
                }
            }

            // Handle client-side errors (HTTP 4xx)
            in 400..499 -> emit(CategoriesResult.Error("Server error. Please try again later."))

            // Handle unexpected server-side errors or unknown status codes
            else -> emit(CategoriesResult.Error("An unexpected error occurred."))
        }

//        } catch (e: IOException) {
        // Emit an error if there is a connection issue
//            emit(CategoriesResult.Error("Connection error. Please check your Internet connection."))
//        } catch (e: UnknownHostException) {
        // Emit an error if there is no Internet connection
//            emit(CategoriesResult.Error("No Internet connection. Please check your connection."))
//        } catch (e: Exception) {
        // Emit a generic error for unexpected exceptions
//            emit(CategoriesResult.Error("An unknown error occurred. Please try again."))
//        }
    }

    /**
     * Fetches a specific item by its ID from the API.
     *
     * This function emits a [Flow] of [ItemResult], representing the result of the operation:
     * - [ItemResult.Success] if the item is successfully retrieved.
     * - [ItemResult.Error] if the item cannot be found or an error occurs during the API call.
     *
     * @param itemId The ID of the item to fetch.
     * @return A [Flow] emitting [ItemResult], containing either the fetched item or an error message.
     */
    fun fetchItem(itemId: Int): Flow<ItemResult> = flow {

//        try {
        val apiResponse = dataService.getItems()

        when (apiResponse.code()) {
            200 -> {
                val items = apiResponse.body()
                if (!items.isNullOrEmpty()) {
                    // Find the item by ID and emit success if found
                    items.toItems().find { it.id == itemId }
                        ?.let { emit(ItemResult.Success(it)) }
                } else {
                    // Emit an error if the response body is empty
                    emit(ItemResult.Error("Product not found."))
                }
            }

            // Handle client-side errors (HTTP 4xx)
            in 400..499 -> emit(ItemResult.Error("Server error. Please try again later."))

            // Handle unexpected server-side errors or unknown status codes
            else -> emit(ItemResult.Error("An unexpected error occurred."))
        }

//        } catch (e: IOException) {
        // Emit an error if there is a connection issue
//            emit(ItemResult.Error("Connection error. Please check your Internet connection."))
//        } catch (e: UnknownHostException) {
        // Emit an error if there is no Internet connection
//            emit(ItemResult.Error("No Internet connection. Please check your connection."))
//        } catch (e: Exception) {
        // Emit a generic error for unexpected exceptions
//            emit(ItemResult.Error("An unknown error occurred. Please try again."))
//        }
    }
}