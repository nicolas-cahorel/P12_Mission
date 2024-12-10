package com.openclassrooms.p12m_joiefull.di

import com.openclassrooms.p12m_joiefull.data.network.ItemsClient
import com.openclassrooms.p12m_joiefull.data.repository.CategoriesRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Koin module for providing network-related dependencies.
 *
 * This module sets up and provides all necessary network-related components such as:
 * - Retrofit for API communication
 * - OkHttpClient for network requests with logging
 * - Moshi for JSON serialization and deserialization
 * It is used to inject these dependencies across the app.
 */
val dataModule: Module = module {

    /**
     * Provides an [OkHttpClient] instance with logging capabilities for network requests.
     *
     * The [HttpLoggingInterceptor] is added to log the network requests and responses at the BODY level.
     * This helps in debugging and monitoring network traffic.
     *
     * @return An [OkHttpClient] configured with logging.
     */
    single {
        OkHttpClient.Builder().apply {
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }.build()
    }

    /**
     * Provides a [Moshi] instance for JSON serialization/deserialization.
     *
     * The [Moshi] instance is configured with the [KotlinJsonAdapterFactory], which is required to handle Kotlin data classes properly.
     *
     * @return A [Moshi] instance to serialize/deserialize JSON data.
     */

    single {
        Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    /**
     * Provides a [Retrofit] instance configured with the base URL, [Moshi] converter, and [OkHttpClient].
     *
     * The [Retrofit] instance is the main component used for making network requests to the API.
     * The [MoshiConverterFactory] is used to convert the JSON response into Kotlin objects.
     *
     * @return A [Retrofit] instance configured for the API requests.
     */
    single {
        Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/OpenClassrooms-Student-Center/D-velopper-une-interface-accessible-en-Jetpack-Compose/main/api/")
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(get())
            .build()
    }

    /**
     * Provides an [ItemsClient] instance created from [Retrofit].
     *
     * This client is used to make API calls related to items (e.g., fetching items data).
     * It is created by Retrofit from the API interface [ItemsClient].
     *
     * @return An instance of [ItemsClient] to make network requests.
     */
    single {
        get<Retrofit>().create(ItemsClient::class.java)
    }

    /**
     * Provides an [CategoriesRepository] instance with dependency on [ItemsClient].
     *
     * The repository abstracts the network layer and provides data to the view models.
     * It relies on [ItemsClient] to fetch the items data from the API.
     *
     * @return An instance of [CategoriesRepository] for accessing items data.
     */
    single { CategoriesRepository(get()) }
}