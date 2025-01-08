package com.openclassrooms.p12m_joiefull.data.network

import com.openclassrooms.p12m_joiefull.data.apiResponse.ItemsApiResponse
import retrofit2.Response
import retrofit2.http.GET

/**
 * Retrofit interface for accessing the items API.
 *
 * This interface defines the HTTP methods required to interact with the items API. It is responsible
 * for fetching data related to items, such as details of available products, and is implemented
 * using Retrofit to handle HTTP requests and responses.
 */
interface ItemsClient {

    /**
     * Fetches the list of items from the API.
     *
     * This method performs a `GET` request to the specified endpoint (`clothes.json`)
     * and retrieves the list of items. The response contains a list of [ItemsApiResponse],
     * each representing an item with its associated attributes such as name, category,
     * price, and image details.
     *
     * This function is marked as `suspend` because it uses Kotlin Coroutines for asynchronous
     * network operations, ensuring non-blocking API calls.
     *
     * @return A [Response] wrapping a list of [ItemsApiResponse]. If the request is successful,
     *         the body contains the list of items. Otherwise, the response will indicate an error.
     */
    @GET("clothes.json")
    suspend fun getItems(): Response<List<ItemsApiResponse>>

}