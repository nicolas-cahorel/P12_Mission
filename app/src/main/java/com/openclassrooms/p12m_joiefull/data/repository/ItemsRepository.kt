package com.openclassrooms.p12m_joiefull.data.repository

import android.util.Log
import com.openclassrooms.p12m_joiefull.data.model.ApiResultModel
import com.openclassrooms.p12m_joiefull.data.model.ItemResultModel
import com.openclassrooms.p12m_joiefull.data.model.PictureResultModel
import com.openclassrooms.p12m_joiefull.data.network.ItemsClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

/**
 * Repository for fetching item data from the API.
 *
 * This class interacts with the [ItemsClient] to fetch item data and processes
 * the response to map it into the required model format.
 *
 * @property dataService The [ItemsClient] instance used to fetch data from the API.
 */
class ItemsRepository(private val dataService: ItemsClient) {

    /**
     * Fetches item data from the API and returns a [Flow] of [ApiResultModel].
     *
     * This function performs a network request to the API, processes the response,
     * and emits the result as a flow. It handles various scenarios such as null response
     * or status code and maps the API response into a custom model.
     *
     * @return A [Flow] of [ApiResultModel] containing the status code and the list of items.
     */
    fun fetchItemsData(): Flow<ApiResultModel> = flow {

        // Make a request to retrieve the item data from the API
        val apiResponse = dataService.getItems()

        // Extract the status code and the response body from the API response
        val apiStatusCode = apiResponse.code()
        val apiResponseBody = apiResponse.body()

        // Create the ApiResultModel based on the API response
        val apiResultModel = when {

            // Case 1: Both response body and status code are available
            apiResponseBody != null && apiStatusCode != null -> {
                ApiResultModel(
                    apiStatusCode,
                    itemsList = apiResponseBody.map { item ->
                        ItemResultModel(
                            id = item.id,
                            picture = PictureResultModel(
                                url = item.picture.url,
                                description = item.picture.description
                            ),
                            name = item.name,
                            category = item.category,
                            likes = item.likes,
                            price = item.price,
                            originalPrice = item.originalPrice
                        )
                    }
                )
            }

            // Case 2: Response body is null, but the status code is available
            apiResponseBody == null && apiStatusCode != null -> {
                ApiResultModel(
                    apiStatusCode,
                    itemsList = emptyList()
                )
            }

            // Case 3: Response body is available, but status code is null
            apiResponseBody != null && apiStatusCode == null -> {
                ApiResultModel(
                    apiStatusCode = 0,
                    itemsList = apiResponseBody.map { item ->
                        ItemResultModel(
                            id = item.id,
                            picture = PictureResultModel(
                                url = item.picture.url,
                                description = item.picture.description
                            ),
                            name = item.name,
                            category = item.category,
                            likes = item.likes,
                            price = item.price,
                            originalPrice = item.originalPrice
                        )
                    }
                )
            }

            // Case 4: Both response body and status code are null
            apiResponseBody == null && apiStatusCode == null -> {
                ApiResultModel(
                    apiStatusCode = 1,
                    itemsList = emptyList()
                )
            }

            // Fallback case to handle unexpected scenarios
            else -> {
                ApiResultModel(
                    apiStatusCode = 2,
                    itemsList = emptyList()
                )
            }
        }

        // Emit the final result model through the flow
        emit(apiResultModel)

    }.catch { error ->
        // Handle errors that may occur during the flow execution (e.g., network errors)
        Log.e("ItemsRepository", "Error fetching items: ${error.message}")
        // Emit a custom result model for network errors
        emit(
            ApiResultModel(
                apiStatusCode = 3, // Custom error code for network-related issues
                itemsList = emptyList() // Return an empty list for network errors
            )
        )
    }
}