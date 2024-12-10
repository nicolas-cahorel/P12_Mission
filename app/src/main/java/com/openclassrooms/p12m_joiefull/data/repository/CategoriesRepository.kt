package com.openclassrooms.p12m_joiefull.data.repository

import com.openclassrooms.p12m_joiefull.data.apiResponse.categorizedItems
import com.openclassrooms.p12m_joiefull.data.model.CategoriesResult
import com.openclassrooms.p12m_joiefull.data.network.ItemsClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Repository for fetching categorized items from the API.
 *
 * This repository is responsible for interacting with the [ItemsClient] to retrieve data,
 * processing the API response, and mapping it to the appropriate domain models.
 *
 * @property dataService The [ItemsClient] instance used to communicate with the API.
 */
class CategoriesRepository(private val dataService: ItemsClient) {

    /**
     * Fetches the list of categories from the API.
     *
     * This function emits a [Flow] of [CategoriesResult], which represents the result of the API call.
     * The result can indicate success with the retrieved categories or various types of errors.
     *
     * @return A [Flow] emitting [CategoriesResult] based on the API response.
     */
    fun fetchCategories(): Flow<CategoriesResult> = flow {
        val apiResponse = dataService.getItems()

        when (apiResponse.code()) {
            200 -> {
                val items = apiResponse.body()
                // Emit success if the API response contains valid items
                if (!items.isNullOrEmpty()) {
                    emit(CategoriesResult.Success(items.categorizedItems()))
                } else {
                    // Emit an error if the response body is empty
                    emit(CategoriesResult.ErrorEmpty)
                }
            }

            // Handle client-side errors (HTTP 4xx)
            in 400..499 -> emit(CategoriesResult.ErrorServer)

            // Handle unexpected server-side errors or unknown status codes
            else -> emit(CategoriesResult.ErrorUnexpected)
        }
    }
}