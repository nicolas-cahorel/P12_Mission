package com.openclassrooms.p12m_joiefull.data.network

import com.openclassrooms.p12m_joiefull.data.apiResponse.ItemsApiResponse
import retrofit2.Response
import retrofit2.http.GET

/**
 * Retrofit interface for accessing the items API.
 *
 * This interface defines the HTTP methods used to fetch data related to items.
 */
interface ItemsClient {

    /**
     * Fetches the list of items from the API.
     *
     * This function performs a GET request to retrieve the list of items from the
     * API endpoint and returns the response wrapped in a [Response] object.
     *
     * @return A [Response] containing a list of [ItemsApiResponse], representing the fetched items.
     */
    @GET("clothes.json")
    suspend fun getItems(): Response<List<ItemsApiResponse>>

}