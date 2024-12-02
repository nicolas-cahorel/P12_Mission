package com.openclassrooms.p12m_joiefull.data.network

import com.openclassrooms.p12m_joiefull.data.apiResponse.ItemsApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface ItemsClient {

    @GET("clothes.json")
    suspend fun getItems(): Response<ItemsApiResponse>

}